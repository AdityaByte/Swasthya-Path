package in.ayush.swasthyapath.event.consumer;

import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;

public interface EventConsumer {

    public void consumeEvent(DoctorConsultedEvent consultedEvent);
}
