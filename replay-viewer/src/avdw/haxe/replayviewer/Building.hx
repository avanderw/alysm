package avdw.haxe.replayviewer;

import openfl.display.Bitmap;
import openfl.display.BitmapData;
import openfl.display.BlendMode;
import openfl.display.Sprite;
import openfl.events.Event;
import openfl.events.MouseEvent;
import openfl.text.TextField;
import openfl.text.TextFieldAutoSize;
import openfl.text.TextFormat;
import openfl.text.TextFormatAlign;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Building extends Sprite
{
	public var type:String;
	public var energy:Int;
	var buildingData:Dynamic;
	public function new(buildingData:Dynamic)
	{
		super();
		this.buildingData = buildingData;
		var bmp:Bitmap = new Bitmap();
		switch (buildingData.buildingType)
		{
			case "ATTACK": bmp.bitmapData = AssetCache.buildingAttack; type = "ATTACK";
			case "DEFENSE": bmp.bitmapData = AssetCache.buildingDefense; type = "DEFENSE";
			case "ENERGY": bmp.bitmapData = AssetCache.buildingEnergy; type = "ENERGY";
			default: bmp.bitmapData = new BitmapData(64, 64, true, 0);
		}

		bmp.y = 2;
		bmp.x = 2;
		if (buildingData.playerType == "B")
		{
			bmp.x = bmp.width + 2;
			bmp.scaleX = -1;
		}

		var statuses:Array<Sprite> = new Array();
		if (buildingData.constructionTimeLeft > 0) {statuses.push(createStatus(AssetCache.statusConstruction, buildingData.constructionTimeLeft));}
		if (buildingData.health > 0 && buildingData.buildingType == "DEFENSE" && buildingData.constructionTimeLeft <= 0) {statuses.push(createStatus(AssetCache.statusHealth, buildingData.health));}
		if (buildingData.weaponCooldownTimeLeft > 0) {statuses.push(createStatus(AssetCache.statusCooldownLeft, buildingData.weaponCooldownTimeLeft));}
		
		addChild(bmp);

		var group:Group = new Group();
		for (i in 0...statuses.length)
		{
			statuses[i].y = i * statuses[i].height + 3;
			statuses[i].x = 4;
			group.addChild(statuses[i]);
		}
		if (statuses.length > 0)
		{
			group.graphics.lineStyle(2);
			group.graphics.beginFill(0xFFFFFF);
			group.graphics.drawRoundRect(0, 0, group.width+7, group.height+3, 8);
			group.graphics.endFill();
		}
		group.y = (64 - group.height) / 2 + 2;
		group.x = (64 - group.width) / 2 + 2;
		addChild(group);

		statuses = new Array();
		if (buildingData.price > 0) {statuses.push(createStatus(AssetCache.statusPrice, buildingData.price));}
		if (buildingData.constructionScore > 0) {statuses.push(createStatus(AssetCache.statusScore, buildingData.constructionScore));}
		if (buildingData.energyGeneratedPerTurn > 0) {statuses.push(createStatus(AssetCache.statusEnergy, buildingData.energyGeneratedPerTurn)); energy = buildingData.energyGeneratedPerTurn; }
		if (buildingData.weaponCooldownPeriod > 0) {statuses.push(createStatus(AssetCache.statusCooldownPeriod, buildingData.weaponCooldownPeriod));}
		if (buildingData.weaponSpeed > 0) {statuses.push(createStatus(AssetCache.statusSpeed, buildingData.weaponSpeed));}
		if (buildingData.weaponDamage > 0) {statuses.push(createStatus(AssetCache.statusDamage, buildingData.weaponDamage));}
		if (buildingData.destroyMultiplier > 0) {statuses.push(createStatus(AssetCache.statusDestroyMultiplier, buildingData.destroyMultiplier));}
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