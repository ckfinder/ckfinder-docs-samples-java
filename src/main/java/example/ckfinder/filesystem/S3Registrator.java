package example.ckfinder.filesystem;

import com.cksource.ckfinder.event.RequestEvent;
import com.cksource.ckfinder.filesystem.FileSystemFactory;
import com.cksource.ckfinder.listener.Listener;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class S3Registrator implements Listener<RequestEvent> {
    @Inject
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(RequestEvent requestEvent) {
        FileSystemFactory fileSystemFactory = applicationContext.getBean(FileSystemFactory.class);
        fileSystemFactory.registerProvider("s3", new S3Provider());
    }
}