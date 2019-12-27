
package com.codebear.keyboard.emoji;

import com.codebear.keyboard.R;
import com.codebear.keyboard.data.EmojiBean;
import com.codebear.keyboard.utils.EmojiParse;

import java.util.HashMap;

/**
 * description:
 * <p>
 * 参照w446108264提供的XhsEmoticonsKeyboard开源键盘解决方案
 * github:https://github.com/w446108264/XhsEmoticonsKeyboard
 * <p>
 * Created by CodeBear on 2017/6/30.
 */

public class DefEmoticons {

    public static final HashMap<String, Integer> emojiMap = new HashMap<>();
    public static final EmojiBean[] sEmojiArray;

    public DefEmoticons() {
    }

    static {
        sEmojiArray = new EmojiBean[]{
//                new EmojiBean(R.mipmap.emoji_0x1f604, EmojiParse.fromCodePoint(128516)),
//                new EmojiBean(R.mipmap.emoji_0x1f603, EmojiParse.fromCodePoint(128515)),
//                new EmojiBean(R.mipmap.emoji_0x1f60a, EmojiParse.fromCodePoint(128522)),
//                new EmojiBean(R.mipmap.emoji_0x1f609, EmojiParse.fromCodePoint(128521)),
//                new EmojiBean(R.mipmap.emoji_0x1f60d, EmojiParse.fromCodePoint(128525)),
//                new EmojiBean(R.mipmap.emoji_0x1f618, EmojiParse.fromCodePoint(128536)),
//                new EmojiBean(R.mipmap.emoji_0x1f61a, EmojiParse.fromCodePoint(128538)),
//                new EmojiBean(R.mipmap.emoji_0x1f61c, EmojiParse.fromCodePoint(128540)),
//                new EmojiBean(R.mipmap.emoji_0x1f61d, EmojiParse.fromCodePoint(128541)),
//                new EmojiBean(R.mipmap.emoji_0x1f633, EmojiParse.fromCodePoint(128563)),
//                new EmojiBean(R.mipmap.emoji_0x1f601, EmojiParse.fromCodePoint(128513)),
//                new EmojiBean(R.mipmap.emoji_0x1f614, EmojiParse.fromCodePoint(128532)),
//                new EmojiBean(R.mipmap.emoji_0x1f60c, EmojiParse.fromCodePoint(128524)),
//                new EmojiBean(R.mipmap.emoji_0x1f612, EmojiParse.fromCodePoint(128530)),
//                new EmojiBean(R.mipmap.emoji_0x1f61e, EmojiParse.fromCodePoint(128542)),
//                new EmojiBean(R.mipmap.emoji_0x1f623, EmojiParse.fromCodePoint(128547)),
//                new EmojiBean(R.mipmap.emoji_0x1f622, EmojiParse.fromCodePoint(128546)),
//                new EmojiBean(R.mipmap.emoji_0x1f602, EmojiParse.fromCodePoint(128514)),
//                new EmojiBean(R.mipmap.emoji_0x1f62d, EmojiParse.fromCodePoint(128557)),
//                new EmojiBean(R.mipmap.emoji_0x1f62a, EmojiParse.fromCodePoint(128554)),
//                new EmojiBean(R.mipmap.emoji_0x1f625, EmojiParse.fromCodePoint(128549)),
//                new EmojiBean(R.mipmap.emoji_0x1f630, EmojiParse.fromCodePoint(128560)),
//                new EmojiBean(R.mipmap.emoji_0x1f613, EmojiParse.fromCodePoint(128531)),
//                new EmojiBean(R.mipmap.emoji_0x1f628, EmojiParse.fromCodePoint(128552)),
//                new EmojiBean(R.mipmap.emoji_0x1f631, EmojiParse.fromCodePoint(128561)),
//                new EmojiBean(R.mipmap.emoji_0x1f620, EmojiParse.fromCodePoint(128544)),
//                new EmojiBean(R.mipmap.emoji_0x1f621, EmojiParse.fromCodePoint(128545)),
//                new EmojiBean(R.mipmap.emoji_0x1f616, EmojiParse.fromCodePoint(128534)),
//                new EmojiBean(R.mipmap.emoji_0x1f637, EmojiParse.fromCodePoint(128567)),
//                new EmojiBean(R.mipmap.emoji_0x1f632, EmojiParse.fromCodePoint(128562)),
//                new EmojiBean(R.mipmap.emoji_0x1f47f, EmojiParse.fromCodePoint(128127)),
//                new EmojiBean(R.mipmap.emoji_0x1f60f, EmojiParse.fromCodePoint(128527)),
//                new EmojiBean(R.mipmap.emoji_0x1f466, EmojiParse.fromCodePoint(128102)),
//                new EmojiBean(R.mipmap.emoji_0x1f467, EmojiParse.fromCodePoint(128103)),
//                new EmojiBean(R.mipmap.emoji_0x1f468, EmojiParse.fromCodePoint(128104)),
//                new EmojiBean(R.mipmap.emoji_0x1f469, EmojiParse.fromCodePoint(128105)),
//                new EmojiBean(R.mipmap.emoji_0x1f31f, EmojiParse.fromCodePoint(127775)),
//                new EmojiBean(R.mipmap.emoji_0x1f444, EmojiParse.fromCodePoint(128068)),
//                new EmojiBean(R.mipmap.emoji_0x1f44d, EmojiParse.fromCodePoint(128077)),
//                new EmojiBean(R.mipmap.emoji_0x1f44e, EmojiParse.fromCodePoint(128078)),
//                new EmojiBean(R.mipmap.emoji_0x1f44c, EmojiParse.fromCodePoint(128076)),
//                new EmojiBean(R.mipmap.emoji_0x1f44a, EmojiParse.fromCodePoint(128074)),
//                new EmojiBean(R.mipmap.emoji_0x270a, EmojiParse.fromChar('✊')),
//                new EmojiBean(R.mipmap.emoji_0x270c, EmojiParse.fromChar('✌')),
//                new EmojiBean(R.mipmap.emoji_0x1f446, EmojiParse.fromCodePoint(128070)),
//                new EmojiBean(R.mipmap.emoji_0x1f447, EmojiParse.fromCodePoint(128071)),
//                new EmojiBean(R.mipmap.emoji_0x1f449, EmojiParse.fromCodePoint(128073)),
//                new EmojiBean(R.mipmap.emoji_0x1f448, EmojiParse.fromCodePoint(128072)),
//                new EmojiBean(R.mipmap.emoji_0x1f64f, EmojiParse.fromCodePoint(128591)),
//                new EmojiBean(R.mipmap.emoji_0x1f44f, EmojiParse.fromCodePoint(128079)),
//                new EmojiBean(R.mipmap.emoji_0x1f4aa, EmojiParse.fromCodePoint(128170)),
//                new EmojiBean(R.mipmap.emoji_0x1f457, EmojiParse.fromCodePoint(128087)),
//                new EmojiBean(R.mipmap.emoji_0x1f380, EmojiParse.fromCodePoint(127872)),
//                new EmojiBean(R.mipmap.emoji_0x2764, EmojiParse.fromChar('❤')),
//                new EmojiBean(R.mipmap.emoji_0x1f494, EmojiParse.fromCodePoint(128148)),
//                new EmojiBean(R.mipmap.emoji_0x1f48e, EmojiParse.fromCodePoint(128142)),
//                new EmojiBean(R.mipmap.emoji_0x1f436, EmojiParse.fromCodePoint(128054)),
//                new EmojiBean(R.mipmap.emoji_0x1f431, EmojiParse.fromCodePoint(128049)),
//                new EmojiBean(R.mipmap.emoji_0x1f339, EmojiParse.fromCodePoint(127801)),
//                new EmojiBean(R.mipmap.emoji_0x1f33b, EmojiParse.fromCodePoint(127803)),
//                new EmojiBean(R.mipmap.emoji_0x1f341, EmojiParse.fromCodePoint(127809)),
//                new EmojiBean(R.mipmap.emoji_0x1f343, EmojiParse.fromCodePoint(127811)),
//                new EmojiBean(R.mipmap.emoji_0x1f319, EmojiParse.fromCodePoint(127769)),
//                new EmojiBean(R.mipmap.emoji_0x2600, EmojiParse.fromChar('☀')),
//                new EmojiBean(R.mipmap.emoji_0x2601, EmojiParse.fromChar('☁')),
//                new EmojiBean(R.mipmap.emoji_0x26a1, EmojiParse.fromChar('⚡')),
//                new EmojiBean(R.mipmap.emoji_0x2614, EmojiParse.fromChar('☔')),
//                new EmojiBean(R.mipmap.emoji_0x1f47b, EmojiParse.fromCodePoint(128123)),
//                new EmojiBean(R.mipmap.emoji_0x1f385, EmojiParse.fromCodePoint(127877)),
//                new EmojiBean(R.mipmap.emoji_0x1f381, EmojiParse.fromCodePoint(127873)),
//                new EmojiBean(R.mipmap.emoji_0x1f4f1, EmojiParse.fromCodePoint(128241)),
//                new EmojiBean(R.mipmap.emoji_0x1f50d, EmojiParse.fromCodePoint(128269)),
//                new EmojiBean(R.mipmap.emoji_0x1f4a3, EmojiParse.fromCodePoint(128163)),
//                new EmojiBean(R.mipmap.emoji_0x26bd, EmojiParse.fromChar('⚽')),
//                new EmojiBean(R.mipmap.emoji_0x2615, EmojiParse.fromChar('☕')),
//                new EmojiBean(R.mipmap.emoji_0x1f37a, EmojiParse.fromCodePoint(127866)),
//                new EmojiBean(R.mipmap.emoji_0x1f382, EmojiParse.fromCodePoint(127874)),
//                new EmojiBean(R.mipmap.emoji_0x1f3e0, EmojiParse.fromCodePoint(127968)),
//                new EmojiBean(R.mipmap.emoji_0x1f697, EmojiParse.fromCodePoint(128663)),
//                new EmojiBean(R.mipmap.emoji_0x1f559, EmojiParse.fromCodePoint(128345))};

                new EmojiBean(R.mipmap.sy_emoji_0001, "[微笑]"),
                new EmojiBean(R.mipmap.sy_emoji_0002, "[打针]"),
                new EmojiBean(R.mipmap.sy_emoji_0003, "[流汗]"),
                new EmojiBean(R.mipmap.sy_emoji_0004, "[拒绝]"),
                new EmojiBean(R.mipmap.sy_emoji_0005, "[不知道]"),
                new EmojiBean(R.mipmap.sy_emoji_0006, "[惊讶]"),
                new EmojiBean(R.mipmap.sy_emoji_0007, "[安慰]"),
                new EmojiBean(R.mipmap.sy_emoji_0008, "[搞怪]"),
                new EmojiBean(R.mipmap.sy_emoji_0009, "[加油]"),
                new EmojiBean(R.mipmap.sy_emoji_0010, "[发送了]"),
                new EmojiBean(R.mipmap.sy_emoji_0011, "[机智]"),
                new EmojiBean(R.mipmap.sy_emoji_0012, "[难过]"),
                new EmojiBean(R.mipmap.sy_emoji_0013, "[戳一下]"),
                new EmojiBean(R.mipmap.sy_emoji_0014, "[看这里]"),
                new EmojiBean(R.mipmap.sy_emoji_0015, "[完美]"),
                new EmojiBean(R.mipmap.sy_emoji_0016, "[惊喜]"),
                new EmojiBean(R.mipmap.sy_emoji_0017, "[得意]"),
                new EmojiBean(R.mipmap.sy_emoji_0018, "[耶]"),
                new EmojiBean(R.mipmap.sy_emoji_0019, "[惊呆]"),
                new EmojiBean(R.mipmap.sy_emoji_0020, "[赞]"),
                new EmojiBean(R.mipmap.sy_emoji_0021, "[喝水]"),
                new EmojiBean(R.mipmap.sy_emoji_0022, "[大笑]"),
                new EmojiBean(R.mipmap.sy_emoji_0023, "[医生]"),
                new EmojiBean(R.mipmap.sy_emoji_0024, "[调皮]"),
                new EmojiBean(R.mipmap.sy_emoji_0025, "[棒棒的]"),
                new EmojiBean(R.mipmap.sy_emoji_0026, "[伤心]"),
                new EmojiBean(R.mipmap.sy_emoji_0027, "[发呆]"),
                new EmojiBean(R.mipmap.sy_emoji_0028, "[憨笑]"),
                new EmojiBean(R.mipmap.sy_emoji_0029, "[闭嘴]"),
                new EmojiBean(R.mipmap.sy_emoji_0030, "[美翻了]"),
                new EmojiBean(R.mipmap.sy_emoji_0031, "[坏笑]"),
        };

    }

    static {
        for(EmojiBean emojiBean : sEmojiArray) {
            emojiMap.put(emojiBean.emoji, emojiBean.icon);
        }
    }

}
