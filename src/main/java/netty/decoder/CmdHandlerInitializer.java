package netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.Getter;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author dongzc15247<br>
 * 开发时间: 2018-01-10<br>
 * <p>
 * <p>
 * 传入协议规范：
 * 传入数据流是一系列的帧，每个帧都由换行符（\n）分隔；
 * 每个帧都由一系列的元素组成，每个元素都由单个空格字符分隔；
 * <p>
 * 一个帧的内容代表一个命令，定义为一个命令名称后跟着数目可变的参数。
 * <p>
 * 我们用于这个协议的自定义解码器将定义以下类：
 * Cmd—将帧（命令）的内容存储在 ByteBuf 中，一个 ByteBuf 用于名称，另一个
 * 用于参数；
 * <p>
 * CmdDecoder—从被重写了的 decode()方法中获取一行字符串，并从它的内容构建
 * 一个 Cmd 的实例
 * <p>
 * CmdHandler—从 CmdDecoder 获取解码的 Cmd 对象，并对它进行一些处理；
 * <p>
 * CmdHandlerInitializer —为了简便起见，我们将会把前面的这些类定义为专门
 * 的 ChannelInitializer 的嵌套类，其将会把这些 ChannelInboundHandler 安装
 * 到 ChannelPipeline 中
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    final static byte SPACE = (byte) ' ';

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
    }

    @Getter
    public static final class Cmd {  // cmd POJO
        private final ByteBuf name;
        private final ByteBuf args;

        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

    }

    public static final class CmdDecoder extends LineBasedFrameDecoder {
        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            //从 ByteBuf 中提取由行尾符序列分隔的帧
            ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
            //如果输入中没有帧，则返回null
            if (frame == null)
                return null;
            //查找第一个空格字符的索引。前面是命令名称，接着是参数
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);
            //使用包含有命令名称和参数的切片创建新的 Cmd 对象
            return new Cmd(frame.slice(frame.readerIndex(), index), frame.slice(index + 1, frame.writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Cmd cmd) throws Exception {
            //处理流入的cmd 对象
        }
    }
}
