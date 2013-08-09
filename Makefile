CFLAGS = -I ~/lib

OBJS = main.o
LIB=~/lib/lib.a
EXE = controls

$(EXE): $(OBJS) $(LIB)
	$(CC) -o $@ $(OBJS) $(LIB)

# pull in dependency info for *existing* .o files
-include $(OBJS:.o=.d)

# compile and generate dependency info
%.o: %.c
	gcc -c $(CFLAGS) $*.c -o $*.o
	gcc -MM $(CFLAGS) $*.c > $*.d

clean:
	-rm $(EXE)  $(OBJS) $(OBJS:.o=.d)
