package avdw.haxe.replayviewer;

import haxe.Json;
import minimalcomps.components.Style;
import openfl.Assets;
import openfl.display.Bitmap;
import openfl.display.BitmapData;
import openfl.display.BlendMode;
import openfl.display.Sprite;
import openfl.display.StageAlign;
import openfl.display.StageScaleMode;
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
	var matchList:ButtonGroup;
	var roundList:ButtonGroup;
	var gameMap:Group;
	var config:Dynamic;
	var font:Font;
	var shield:BitmapData;
	var attackBuildingBmd:BitmapData;
	var energy:BitmapData;
	var fields:BitmapData;
	var warning:BitmapData;
	var missile:BitmapData;
	var buildingDetails:BuildingDetails;
	var playerDetails:PlayerDetails;

	public function new()
	{
		super();
		// Assets:
		// openfl.Assets.getBitmapData("img/assetname.jpg");
		try
		{
			new AssetCache();
			font = Assets.getFont("font/OpenSans-Regular.ttf");

			shield = Assets.getBitmapData("img/64x-building-defense.png");
			attackBuildingBmd = Assets.getBitmapData("img/64x-building-attack.png");
			energy = Assets.getBitmapData("img/64x-building-energy.png");
			fields = AssetCache.buildingEmpty;
			warning = Assets.getBitmapData("img/warning.png");
			missile = Assets.getBitmapData("img/missile.png");

			config = Json.parse(File.getContent('../../../config.json'));

			addChild(matchList = new ButtonGroup());
			addChild(roundList = new ButtonGroup());
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

		stage.scaleMode = StageScaleMode.NO_SCALE;
		stage.align = StageAlign.TOP_LEFT;

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
			var buildingSize:Int = 64;
			var spacingSize:Int = 4;
			var map = Json.parse(File.getContent(directory + "/Player 1/JsonMap.json"));

			if (buildingDetails == null)
			{
				buildingDetails = new BuildingDetails(map.gameDetails);
				buildingDetails.x = stage.stageWidth - buildingDetails.width;
				buildingDetails.y = stage.stageHeight - buildingDetails.height;
				addChild(buildingDetails);
			}

			trace(map);
			//trace(map.gameMap);

			for (row in cast (map.gameMap, Array<Dynamic>))
			{
				for (cell in cast (row, Array<Dynamic>))
				{
					trace(cell, cell.cellOwner);


					var buildings:Array<Dynamic> = cell.buildings;
					var buildingBmp:Building = (buildings.length > 0) ? new Building(buildings[0]) : new Building({});
					if (buildings.length > 1)
					{
						throw "more than one building on a cell, handle this";
					}

					var missiles:Array<Dynamic> = cell.missiles;
					var missileBmp:Bitmap = new Bitmap();
					if (missiles.length > 0)
					{
						if (missiles.length > 1)
						{
							throw "more than one missile on a cell, handle this";
						}

						missileBmp.bitmapData = missile;
						if (missiles[0].playerType == "A")
						{
							missileBmp.x = missileBmp.width;
							missileBmp.scaleX = -1;
						}
					}

					var sprite:Sprite = new Sprite();
					sprite.addChild(buildingBmp);
					sprite.addChild(missileBmp);
					sprite.x = cell.x * (buildingSize + spacingSize);
					sprite.y = cell.y * (buildingSize + spacingSize);
					gameMap.add(sprite);
				}
			}
			if (playerDetails == null)
			{
				addChild(playerDetails = new PlayerDetails());
				playerDetails.x = gameMap.x + (gameMap.width - playerDetails.width) / 2;
				playerDetails.y = gameMap.y + gameMap.height;
			}

			playerDetails.update(map.players);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

}
