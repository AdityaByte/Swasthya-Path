package in.ayush.swasthyapath.event.producer;

import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;

public interface EventProducer {

    public void produceEvent(DoctorConsultedEvent consultedEvent);
}
