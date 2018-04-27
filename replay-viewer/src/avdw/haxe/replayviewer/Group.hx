package avdw.haxe.replayviewer;
import flash.display.Sprite;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Group extends Sprite
{
	var sprites:Array<Sprite> = new Array();

	public function new()
	{
		super();
	}

	public function add(sprite:Sprite)
	{
		try
		{
			sprites.push(sprite);
			addChild(sprite);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function removeAll()
	{
		for (sprite in sprites)
		{
			removeChild(sprite);
		}

		while (sprites.length > 0)
		{
			sprites.pop();
		}
	}

}