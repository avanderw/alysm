package avdw.haxe.replayviewer;

import flash.geom.Point;
import openfl.display.Sprite;
import openfl.display.Bitmap;
import openfl.display.BitmapData;
import openfl.display.BlendMode;
import openfl.display.Sprite;
import openfl.events.Event;
import openfl.events.MouseEvent;
import openfl.filters.ColorMatrixFilter;
import openfl.geom.Matrix;
import openfl.text.TextField;
import openfl.text.TextFieldAutoSize;
import openfl.text.TextFormat;
import openfl.text.TextFormatAlign;
/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Missile extends Sprite
{
	var missile:Dynamic;

	public function new(missile:Dynamic)
	{
		super();
		try
		{
			this.missile = missile;

			var missileBmp:Bitmap = new Bitmap(AssetCache.missile);
			if (missile.playerType == "A")
			{
				missileBmp.x = missileBmp.width;
				missileBmp.scaleX = -1;
			}

			addChild(missileBmp);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}

		var statuses:Array<Sprite> = new Array();
		if (missile.speed > 0) {statuses.push(createStatus(AssetCache.statusSpeed, missile.speed));}
		if (missile.damage > 0) {statuses.push(createStatus(AssetCache.statusDamage, missile.damage)); }
		
		tooltip = new Group();
		for (i in 0...statuses.length)
		{
			statuses[i].y = i * statuses[i].height + 3;
			statuses[i].x = 4;
			tooltip.addChild(statuses[i]);
		}
		if (statuses.length > 0)
		{
			tooltip.graphics.lineStyle(2);
			tooltip.graphics.beginFill(0xFFFFFF);
			tooltip.graphics.drawRoundRect(0, 0, tooltip.width+7, tooltip.height+3, 8);
			tooltip.graphics.endFill();
		}

		addEventListener(MouseEvent.MOUSE_OVER, addTooltip);
		addEventListener(MouseEvent.MOUSE_OUT, removeTooltip);
		addEventListener(MouseEvent.MOUSE_MOVE, moveTooltip);
		addEventListener(Event.REMOVED_FROM_STAGE, removed);
	}

	function removed(e:Event):Void
	{
		removeEventListener(Event.REMOVED_FROM_STAGE, removed);
		removeEventListener(MouseEvent.MOUSE_OVER, addTooltip);
		removeEventListener(MouseEvent.MOUSE_OUT, removeTooltip);
		removeEventListener(MouseEvent.MOUSE_MOVE, moveTooltip);
	}

	function moveTooltip(e:MouseEvent):Void
	{
		if (stage.contains(tooltip))
		{
			tooltip.x = stage.mouseX + 16;
			tooltip.y = stage.mouseY + 16;
		}
	}

	function addTooltip(e:MouseEvent):Void
	{
		stage.addChild(tooltip);
		tooltip.x = stage.mouseX + 16;
		tooltip.y = stage.mouseY + 16;
	}

	function removeTooltip(e:MouseEvent):Void
	{
		stage.removeChild(tooltip);
	}
	var format:TextFormat;
	var tooltip:Group;
	function createStatus(bmd:BitmapData, value:String):Sprite
	{
		var sprite:Sprite = new Sprite();
		try {
			if (format == null)
			{
				format = new TextFormat(AssetCache.font.fontName, 16);
				format.align = TextFormatAlign.LEFT;
			}

			var bmp:Bitmap = new Bitmap(bmd);
			var tf:TextField = new TextField();
			tf.selectable = false;
			tf.setTextFormat(format);
			tf.text = "" + value;
			tf.autoSize = TextFieldAutoSize.LEFT;
			tf.height = tf.textHeight;
			tf.x = bmp.width;
			tf.y = -2;

			sprite.addChild(bmp);
			sprite.addChild(tf);
		}
		catch (e:Dynamic) {trace(e);}
		return sprite;
	}
}