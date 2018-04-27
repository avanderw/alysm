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
	var _width:Int = 150;
	var _height:Int;
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
			tf.width = _width;
			tf.autoSize = TextFieldAutoSize.CENTER;
			tf.y = Math.round(tf.height / 2);

			_height = Math.round(tf.height * 2);

			bg = new Sprite();
			deselect();
			addChild(bg);
			addChild(tf);

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
			bg.graphics.lineStyle(1);
			bg.graphics.beginFill(0xeeeeee);
			bg.graphics.drawRoundRect(0, 0, _width, _height, 5);
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
		bg.graphics.lineStyle(1);
		bg.graphics.beginFill(0xdddddd);
		bg.graphics.drawRoundRect(0, 0, _width, _height, 5);
		bg.graphics.endFill();
	}

}