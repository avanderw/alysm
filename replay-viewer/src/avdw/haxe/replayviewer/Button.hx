package avdw.haxe.replayviewer;

import openfl.Assets;
import openfl.display.Sprite;
import openfl.events.Event;
import openfl.events.MouseEvent;
import openfl.text.TextField;
import openfl.text.TextFieldAutoSize;
import openfl.text.TextFormat;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Button extends Sprite
{
	var padding:Int = 2;
	var bg:Sprite;
	public function new(txt:String, handler:Dynamic->Void)
	{
		super();

		try
		{
			var tf:TextField = new TextField();
			tf.setTextFormat(new TextFormat(Assets.getFont("font/OpenSans-Regular.ttf").fontName, 12));
			tf.selectable = false;
			tf.text = txt;
			tf.x = tf.y = padding +1;
			tf.autoSize = TextFieldAutoSize.LEFT;

			bg = new Sprite();
			addChild(bg);
			addChild(tf);
			deselect();

			addEventListener(MouseEvent.CLICK, handler);
			addEventListener(Event.SELECT, handler);

		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function select()
	{
		try
		{
			bg.graphics.clear();
			bg.graphics.lineStyle(2);
			bg.graphics.beginFill(0xfefefe);
			bg.graphics.drawRoundRect(padding, padding, width+2*padding, height+2*padding, 5);
			bg.graphics.endFill();
			
			dispatchEvent(new Event(Event.SELECT));
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function deselect()
	{
		bg.graphics.clear();
		bg.graphics.lineStyle(2);
		bg.graphics.beginFill(0xdddddd);
		bg.graphics.drawRoundRect(padding, padding, width+2*padding, height+2*padding, 5);
		bg.graphics.endFill();
	}

}