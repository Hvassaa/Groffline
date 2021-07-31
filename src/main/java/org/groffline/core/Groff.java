package org.groffline.core;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Groff {
    enum Macro {
        MS("ms"),
        ME("me"),
        MM("mm"),
        MOM("mom");

        private final String s;

        Macro(final String s) {
            this.s = s;
        }

        @Override
        public final String toString() {
            return s;
        }
    }

    public static byte[] render(String input, Groff.Macro macro) {
        try {
            final String uuid = UUID.randomUUID().toString().replace("-","");
            String pathString = "/tmp/"+uuid+"."+macro;
            Path pathForTmpFile = Paths.get(pathString);
            Files.write(pathForTmpFile, input.getBytes(StandardCharsets.UTF_8));

            ProcessBuilder pb = new ProcessBuilder("groff", "-t", "-e", "-"+macro, "-Tpdf", pathString);
            Process p = pb.start();
            p.waitFor();
            InputStream stream = p.getInputStream();
            byte[] outputBytes = stream.readAllBytes();
            stream.close();
            p.destroy();
            File file = new File(pathString);
            file.delete();
            return outputBytes;
        } catch (Exception e) {
            // TODO better error handling
            return null;
        }
    }

}
