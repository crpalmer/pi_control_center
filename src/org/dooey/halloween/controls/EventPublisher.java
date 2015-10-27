package org.dooey.halloween.controls;

public interface EventPublisher {
	void publish();

	void publish(byte[] data);
}
