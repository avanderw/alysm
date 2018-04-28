package avdw.haxe.replayviewer;

import openfl.display.Sprite;
import openfl.display.DisplayObject;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Group extends Sprite
{
	var displayObjs:Array<DisplayObject> = new Array();

	public function new()
	{
		super();
	}

	public function add(displayObj:DisplayObject)
	{
		try
		{
			displayObjs.push(displayObj);
			addChild(displayObj);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function removeAll()
	{
		for (displayObj in displayObjs)
		{
			removeChild(displayObj);
		}

		while (displayObjs.length > 0)
		{
			displayObjs.pop();
		}
	}

}