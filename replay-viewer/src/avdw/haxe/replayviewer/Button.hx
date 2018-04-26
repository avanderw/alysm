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
	public function new(txt:String, handler:Dynamic->Void, ?x:Int, ?y:Int)
	{
		super();

		if (y == null && x != null)
		{
			y = x;
		}
		else if (y == null && x == null)
		{
			x = y = 0;
		}
		this.x = x;
		this.y = y;
		
		var tf:TextField = new TextField();
		tf.setTextFormat(new TextFormat(Assets.getFont("font/OpenSans-Regular.ttf").fontName, 12));
		tf.selectable = false;
		tf.text = txt;
		tf.width = 100;
		tf.autoSize = TextFieldAutoSize.CENTER;
		tf.y = Math.round(tf.height / 2);
		
		var bg:Sprite = new Sprite();
		bg.graphics.lineStyle(1);
		bg.graphics.beginFill(0xdddddd);
		bg.graphics.drawRoundRect(0, 0, 200, Math.round(tf.height * 2), 5);
		bg.graphics.endFill();
		addChild(bg);
		addChild(tf);
		
		addEventListener(MouseEvent.CLICK, handler);
	}

}