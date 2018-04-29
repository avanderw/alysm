package avdw.haxe.replayviewer;
import openfl.display.BlendMode;
import openfl.text.TextField;
import openfl.display.Bitmap;
import openfl.display.BitmapData;
import openfl.display.Sprite;
import openfl.text.TextFieldAutoSize;
import openfl.text.TextFormat;
import openfl.text.TextFormatAlign;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Building extends Sprite
{

	public function new(buildingData:Dynamic)
	{
		super();
		var bmp:Bitmap = new Bitmap();
		switch (buildingData.buildingType)
		{
			case "ATTACK": bmp.bitmapData = AssetCache.buildingAttack;
			case "DEFENSE": bmp.bitmapData = AssetCache.buildingDefense;
			case "ENERGY": bmp.bitmapData = AssetCache.buildingEnergy;
			default: bmp.bitmapData = AssetCache.buildingEmpty;
		}

		if (buildingData.playerType == "B")
		{
			bmp.x = bmp.width;
			bmp.scaleX = -1;
		}

		var statuses:Array<Sprite> = new Array();
		if (buildingData.constructionTimeLeft > 0) {statuses.push(createStatus(AssetCache.statusConstruction, buildingData.constructionTimeLeft));}
		//if (buildingData.weaponCooldownPeriod > 0) {statuses.push(createStatus(AssetCache.statusCooldownPeriod, buildingData.weaponCooldownPeriod));}
		//if (buildingData.price > 0) {statuses.push(createStatus(AssetCache.statusPrice, buildingData.price));}
		//if (buildingData.weaponSpeed > 0) {statuses.push(createStatus(AssetCache.statusSpeed, buildingData.weaponSpeed));}
		//if (buildingData.destroyMultiplier > 0) {statuses.push(createStatus(AssetCache.statusDestroyMultiplier, buildingData.destroyMultiplier));}
		//if (buildingData.energyGeneratedPerTurn > 0) {statuses.push(createStatus(AssetCache.statusEnergy, buildingData.energyGeneratedPerTurn));}
		if (buildingData.health > 0 && buildingData.buildingType == "DEFENSE" && buildingData.constructionTimeLeft <= 0) {statuses.push(createStatus(AssetCache.statusHealth, buildingData.health));}
		if (buildingData.weaponCooldownTimeLeft > 0) {statuses.push(createStatus(AssetCache.statusCooldownLeft, buildingData.weaponCooldownTimeLeft));}
		//if (buildingData.weaponDamage > 0) {statuses.push(createStatus(AssetCache.statusDamage, buildingData.weaponDamage));}
		//if (buildingData.constructionScore > 0) {statuses.push(createStatus(AssetCache.statusScore, buildingData.constructionScore));}

		var colorOverlay:Sprite = new Sprite();
		colorOverlay.blendMode = BlendMode.ADD;
		switch (buildingData.playerType)
		{
			case "A": colorOverlay.graphics.beginFill(0xFF0000);
			case "B": colorOverlay.graphics.beginFill(0x0000FF);
			default: colorOverlay.graphics.beginFill();
		}

		colorOverlay.graphics.drawRect(0, 0, 64, 64);
		colorOverlay.graphics.endFill();

		addChild(bmp);
		addChild(colorOverlay);

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
		group.y = (64 - group.height) / 2;
		group.x = (64 - group.width) / 2;
		addChild(group);

		graphics.lineStyle(2);
		graphics.beginFill(0xFFFFFF);
		graphics.drawRoundRect(0, 0, width, height, 16);
		graphics.endFill();
	}

	var format:TextFormat;
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
			tf.setTextFormat(format);
			tf.text = "" + value;
			tf.autoSize = TextFieldAutoSize.LEFT;
			tf.height = tf.textHeight;
			tf.x = bmp.width;

			sprite.addChild(bmp);
			sprite.addChild(tf);
		}
		catch (e:Dynamic) {trace(e);}
		return sprite;
	}
}