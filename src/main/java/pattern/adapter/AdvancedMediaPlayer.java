package pattern.adapter;

/**
 * @author zhaojiatao
 * @date 2019/2/27
 * 为高级媒体播放器创建接口
 */
public interface AdvancedMediaPlayer {
    public void playVlc(String fileName);
    public void playMp4(String fileName);
}
