package avdw.haxe.replayviewer;

import haxe.Json;
import minimalcomps.components.Style;
import openfl.Assets;
import openfl.display.Bitmap;
import openfl.display.BitmapData;
import openfl.display.BlendMode;
import openfl.display.Sprite;
import openfl.events.Event;
import openfl.text.Font;
import sys.FileSystem;
import sys.io.File;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Main extends Sprite
{
	var matchList:VGroup;
	var roundList:VGroup;
	var gameMap:Group;
	var config:Dynamic;
	var font:Font;
	var shield:BitmapData;
	var sword:BitmapData;
	var energy:BitmapData;
	var fields:BitmapData;
	var warning:BitmapData;

	public function new()
	{
		super();

		// Assets:
		// openfl.Assets.getBitmapData("img/assetname.jpg");
		try
		{
			font = Assets.getFont("font/OpenSans-Regular.ttf");
			shield = Assets.getBitmapData("img/shield.png");
			sword = Assets.getBitmapData("img/sword.png");
			energy = Assets.getBitmapData("img/energy.png");
			fields = Assets.getBitmapData("img/fields.png");
			warning = Assets.getBitmapData("img/warning.png");
			config = Json.parse(File.getContent('../../../config.json'));
			

			addChild(matchList = new VGroup());
			addChild(roundList = new VGroup());
			addChild(gameMap = new Group());

			addEventListener(Event.ADDED_TO_STAGE, added);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	function added(e:Event):Void
	{
		removeEventListener(Event.ADDED_TO_STAGE, added);

		updateMatchList();
		resize();
	}

	function resize()
	{
		try
		{
			roundList.x = matchList.width;
			gameMap.x = roundList.x + roundList.width;
			matchList.invalidate();
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	function updateMatchList()
	{
		try
		{
			var matches = FileSystem.readDirectory(config.matchDirectory);

			for (match in matches)
			{
				var btn = new Button(match, function(e:Dynamic)
				{
					updateRoundList(config.matchDirectory + "/" + match);
				});
				matchList.add(btn);
			}
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	function updateRoundList(directory:String)
	{
		try
		{
			var rounds = FileSystem.readDirectory(directory);
			roundList.removeAll();
			for (round in rounds)
			{
				var btn = new Button(round, function(e:Dynamic)
				{
					gameMap.removeAll();
					loadRound(directory + "/" + round);
				});
				roundList.add(btn);
			}
			resize();
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	function loadRound(directory:String)
	{
		try
		{
			var map = Json.parse(File.getContent(directory + "/Player 1/JsonMap.json"));
			trace(map.gameMap);

			for (row in cast (map.gameMap, Array<Dynamic>))
			{
				for (cell in cast (row, Array<Dynamic>))
				{
					trace(cell, cell.cellOwner);
					var buildings:Array<Dynamic> = cell.buildings;

					var overlay:Sprite = new Sprite();
					overlay.blendMode = BlendMode.ADD;
					if (cell.cellOwner == "A")
					{
						overlay.graphics.beginFill(0xFF0000);
					}
					else if (cell.cellOwner == "B")
					{
						overlay.graphics.beginFill(0x0000FF);
					}
					else
					{
						overlay.graphics.beginFill();
					}
					overlay.graphics.drawRect(0, 0, 32, 32);
					overlay.graphics.endFill();

					var bitmap:Bitmap = new Bitmap(warning);
					if (buildings.length > 0)
					{
						if (buildings.length > 1)
						{
							throw "more than one building on a cell, handle this";
						}

						if (buildings[0].buildingType  == "ENERGY")
						{
							bitmap = new Bitmap(energy);
						}
						else if (buildings[0].buildingType  == "ATTACK")
						{
							bitmap = new Bitmap(sword);
						}
						else if (buildings[0].buildingType  == "DEFENSE")
						{
							bitmap = new Bitmap(shield);
						}
					}
					else
					{
						bitmap = new Bitmap(fields);
					}
					
					var sprite:Sprite = new Sprite();
					sprite.addChild(bitmap);
					sprite.addChild(overlay);
					sprite.x = cell.x * (32 + 2);
					sprite.y = cell.y * (32 + 2);
					gameMap.add(sprite);
				}

			}
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

}
