import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

interface SongPlayerListener {
    void onSongEnd();
}

public class SongPlayer {

    private SongPlayerListener listener;

    public void setListener(SongPlayerListener listener) {
        this.listener = listener;
    }

    public void playSong(String filePath) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Void> future = executor.submit(() -> {
            try {
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Audio Line not supported");
                    return null;
                }

                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioStream);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.setFramePosition(0); // Restart the clip
                        clip.start();
                        if (listener != null) {
                            listener.onSongEnd();
                        }
                    }
                });

                clip.start();
                return null;
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                e.printStackTrace();
                return null;
            }
        });

        executor.shutdown();
        try {
            future.get(); // Wait for audio playback to finish (not blocking the main thread)
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}