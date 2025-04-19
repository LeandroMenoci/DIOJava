public class Main {
    public static void main(String[] args) {

        MusicPlayer musicPlayer = new Computer();
        VideoPlayer videoPlayer = new Smartphone();

        runMusic(musicPlayer);
        runVideo(videoPlayer);
        runMusic(new MusicBox());

    }

    public static void runVideo(VideoPlayer videoPlayer) {
        videoPlayer.playVideo();
    }

    public static void runMusic(MusicPlayer musicPlayer) {
        musicPlayer.playMusic();
    }
}