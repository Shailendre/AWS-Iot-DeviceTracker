package info.lazycompiler;

import info.lazycompiler.configuration.SpringConfiguration;
import info.lazycompiler.core.Publisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        Publisher publisher = (Publisher) context.getBean("publisher");

        // publish to topic
        publisher.publish();
    }
}
