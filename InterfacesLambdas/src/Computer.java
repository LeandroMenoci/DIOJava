public class Computer implements VideoPlayer, MusicPlayer{
    @Override
    public void playVideo() {
        System.out.println("O Computador está Reproduzindo o vídeo");
    }

    @Override
    public void pauseVideo() {
        System.out.println("O Computador está Pausando o vídeo");
    }

    @Override
    public void stopVideo() {
        System.out.println("O Computador está Parando o vídeo");
    }

    @Override
    public void playMusic() {
        System.out.println("O Computador está Tocando a música");
    }

    @Override
    public void pauseMusic() {
        System.out.println("O Computador está Pausando a música");
    }

    @Override
    public void stopMusic() {
        System.out.println("O Computador está Parando a música");

    }
}
