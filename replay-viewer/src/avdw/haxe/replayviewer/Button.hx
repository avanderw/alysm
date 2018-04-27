package avdw.haxe.replayviewer;

import flash.display.Sprite;
import flash.events.MouseEvent;
import flash.text.TextFormat;
import openfl.Assets;
import openfl.text.TextField;
import openfl.text.TextFieldAutoSize;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Button extends Sprite
{
	var _width:Int = 150;
	public function new(txt:String, handler:Dynamic->Void)
	{
		super();

		try
		{
			var tf:TextField = new TextField();
			tf.setTextFormat(new TextFormat(Assets.getFont("font/OpenSans-Regular.ttf").fontName, 12));
			tf.selectable = false;
			tf.text = txt;
			tf.width = _width;
			tf.autoSize = TextFieldAutoSize.CENTER;
			tf.y = Math.round(tf.height / 2);

			var bg:Sprite = new Sprite();
			bg.graphics.lineStyle(1);
			bg.graphics.beginFill(0xdddddd);
			bg.graphics.drawRoundRect(0, 0, _width, Math.round(tf.height * 2), 5);
			bg.graphics.endFill();
			addChild(bg);
			addChild(tf);

			addEventListener(MouseEvent.CLICK, handler);

		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

}