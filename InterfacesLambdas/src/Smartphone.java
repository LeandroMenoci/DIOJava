public class Smartphone implements VideoPlayer, MusicPlayer{
    @Override
    public void playVideo() {
        System.out.println("O Smartphone está Reproduzindo o vídeo");
    }

    @Override
    public void pauseVideo() {
        System.out.println("O Smartphone está Pausando o vídeo");
    }

    @Override
    public void stopVideo() {
        System.out.println("O Smartphone está Parando o vídeo");
    }

    @Override
    public void playMusic() {
        System.out.println("O Smartphone está Tocando a música");
    }

    @Override
    public void pauseMusic() {
        System.out.println("O Smartphone está Pausando a música");
    }

    @Override
    public void stopMusic() {
        System.out.println("O Smartphone está Parando a música");
    }
}
