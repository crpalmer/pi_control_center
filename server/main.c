#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <netinet/in.h>
#include "gpio.h"
#include "net.h"
#include "net-line-reader.h"
#include "mem.h"
#include "global-trace.h"

#define ZOMBIE	"zombie"

static gpio_table_t gpio_table[] =  {
    { ZOMBIE,		17,	false },
};

#define N_GPIO_TABLE (sizeof(gpio_table) / sizeof(gpio_table[0]))

#define PORT    5555

static void
command(void *gpio_as_vp, const char *line)
{
    gpio_t *gpio = (gpio_t *) gpio_as_vp;
    const char *cmd = line;
    const char *actor;
    int n_cmd;

    if ((actor = strchr(line, ' ')) == NULL) {
	fprintf(stderr, "Malformed command [%s]\n", line);
	return;
    }

    n_cmd = actor - cmd;
    actor++;

    if (strncmp(cmd, "ON", n_cmd) == 0) { 
	gpio_on(gpio, actor);
    } else if (strncmp(cmd, "OFF", n_cmd) == 0) {
	gpio_off(gpio, actor);
    } else {
	fprintf(stderr, "Unknown command [%s] in [%s]\n", cmd, line);
    }
}

int
main(void)
{
    int sock;
    fd_set active_fd_set, read_fd_set;
    int i;
    struct sockaddr_in clientname;
    size_t size;
    gpio_t *gpio;
    net_line_reader_t **readers;

    ENABLE_GLOBAL_TRACE();

    gpio = gpio_new(gpio_table, N_GPIO_TABLE);

    sock = net_listen(PORT);
    if (sock < 0) {
	perror("net_listent");
	exit(1);
    }

    readers = fatal_malloc(sizeof(*readers) * FD_SETSIZE);

    /* Initialize the set of active sockets. */
    FD_ZERO(&active_fd_set);
    FD_SET(sock, &active_fd_set);

    while (1) {
	/* Block until input arrives on one or more active sockets. */
	read_fd_set = active_fd_set;
	if (select(FD_SETSIZE, &read_fd_set, NULL, NULL, NULL) < 0) {
	    perror("select");
	    exit(EXIT_FAILURE);
	 }

	/* Service all the sockets with input pending. */
	for (i = 0; i < FD_SETSIZE; ++i) {
	    if (FD_ISSET(i, &read_fd_set)) {
		if (i == sock) {
		    /* Connection request on original socket. */
		    int new;
		    size = sizeof(clientname);
		    new = accept(sock, (struct sockaddr *) &clientname, &size);
		    if (new < 0) {
			perror("accept");
			exit(EXIT_FAILURE);
		    }
		    fprintf(stderr,
			     "Server: connect from host %s, port %hd.\n",
			     inet_ntoa(clientname.sin_addr),
			   ntohs(clientname.sin_port));
		    FD_SET(new, &active_fd_set);
		    readers[new] = net_line_reader_new(new, command, gpio);
		} else {
		    /* Data arriving on an already-connected socket. */
		    if (net_line_reader_read(readers[i]) < 0){
			perror("read");
			net_line_reader_destroy(readers[i]);
			close(i);
			FD_CLR(i, &active_fd_set);
		    }
		}
	    }
	}
    }
}
