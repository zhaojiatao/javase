package pattern.adapter;

/**
 * @author zhaojiatao
 * @date 2019/2/27
 * 创建实现了 MediaPlayer 接口的适配器类
 *
 * 注意这个类只有一个有参的构造方法，没有重写无参的构造方法
 */
public class MediaAdapter implements MediaPlayer {

    AdvancedMediaPlayer advancedMusicPlayer;

    /**
     * 适配器类构造方法，根据指定的媒体类型，来指定使用哪个高级的媒体播放器
     * @param audioType
     */
    public MediaAdapter(String audioType){
        if(audioType.equalsIgnoreCase("vlc") ){
            advancedMusicPlayer = new VlcPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")){
            advancedMusicPlayer = new Mp4Player();
        }
    }

    /**
     * 在指定了具体的媒体播放器后，开始调用具体的播放方法
     * @param audioType
     * @param fileName
     */
    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("vlc")){
            advancedMusicPlayer.playVlc(fileName);
        }else if(audioType.equalsIgnoreCase("mp4")){
            advancedMusicPlayer.playMp4(fileName);
        }
    }
}
