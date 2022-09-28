package dahuashejimoshi.adapter;

/**
 * @author zhaojiatao
 * @date 2019/2/27
 * 我们的目标是使用 AudioPlayer 来播放不同类型的音频格式。
 * 但是，mp3是普通播放器就可以播放的，而vlc和mp4是需要更高级的播放器才能播放，所以在audioPlayer内部，需要使用适配器根据不同的媒体类型适配更高级的播放器
 *
 * 就像此类，对于使用者来说，是不知道audioPlayer内部的适配情况的，他只需要给定媒体类型，然后指定媒体文件就可以。
 */
public class AdapterPatternDemo {
    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }
}