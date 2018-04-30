package avdw.haxe.replayviewer;

import flash.display.Bitmap;
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
	var dark:UInt;
	var light:UInt;
	var tf:TextField;
	public function new(txt:String, handler:Dynamic->Void, dark:UInt = 0x000000, light:UInt = 0xdddddd)
	{
		super();
		this.light = light;
		this.dark = dark;

		try
		{
			var energyBmp:Bitmap = null;
			if (txt.indexOf("Energy") > 0)
			{
				energyBmp = new Bitmap(AssetCache.statusEnergy);
				energyBmp.x = 5;
				energyBmp.y = 23;
				txt = txt.substring(0, txt.indexOf("Energy")) + "  " + txt.substring(txt.indexOf("Energy:") + "Energy:".length);
			}

			tf = new TextField();
			tf.setTextFormat(new TextFormat(AssetCache.font.fontName, 16));
			tf.selectable = false;
			tf.text = txt;
			tf.x = tf.y = padding +1;
			tf.autoSize = TextFieldAutoSize.LEFT;

			bg = new Sprite();
			addChild(bg);
			addChild(tf);
			deselect();

			if (energyBmp != null)
			{
				addChild(energyBmp);
			}

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
			bg.graphics.lineStyle(2, light);
			bg.graphics.beginFill(dark);
			bg.graphics.drawRoundRect(padding, padding, width+2*padding, height+2*padding, 5);
			bg.graphics.endFill();
			tf.textColor = 0xFFFFFF;
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
		bg.graphics.lineStyle(2, dark);
		bg.graphics.beginFill(light);
		bg.graphics.drawRoundRect(padding, padding, width+2*padding, height+2*padding, 5);
		bg.graphics.endFill();
		tf.textColor = 0;
	}

}