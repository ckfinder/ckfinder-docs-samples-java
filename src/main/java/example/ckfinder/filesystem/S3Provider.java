package example.ckfinder.filesystem;

import com.cksource.ckfinder.filesystem.provider.FileSystemProvider;
import com.upplication.s3fs.S3FileSystemProvider;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.Map;

public class S3Provider implements FileSystemProvider {
    @Override
    public FileSystem getFileSystem(Map<String, Object> env) throws Exception {
        S3FileSystemProvider fsp = new S3FileSystemProvider();

        return fsp.getFileSystem(URI.create("s3:///"), env);
    }
}
