package recorder;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class SpecializedScreenRecorder extends ScreenRecorder {

    private String name;

    public SpecializedScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, File movieFolder, String name) throws Exception {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, null, movieFolder);
        this.name = name;
    }

    public SpecializedScreenRecorder(File movieFolder, String name) throws Exception {
        this(
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(),
            new Rectangle(0, 0, 1920, 1080),
            new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_JPEG, CompressorNameKey, ENCODING_QUICKTIME_JPEG, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_BLACK_CURSOR, FrameRateKey, Rational.valueOf(30)),
            movieFolder,
            name
        );
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        return new File(movieFolder, name + "." + Registry.getInstance().getExtension(fileFormat));
    }
}
