package avdw.haxe.replayviewer;
import openfl.display.Sprite;
import openfl.events.Event;
import openfl.events.MouseEvent;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class ButtonGroup extends Sprite
{
	var selectedIdx:Int = 0;
	var buttons:Array<Button> = new Array();
	var invalid:Bool = true;
	var addedTo:Bool = false;

	public function new()
	{
		super();
		addEventListener(Event.ENTER_FRAME, function(e:Dynamic)
		{
			try
			{
				if (invalid)
				{
					resize();
					buttons[selectedIdx].select();
					invalid = false;

					if (addedTo)
					{
						graphics.clear();
						graphics.beginFill(0xFFFFFF);
						graphics.drawRect(0, 0, width, height);
						graphics.endFill();
						addedTo = false;
					}
				}
			}
			catch (e:Dynamic)
			{
				trace(e);
			}
		});
		addEventListener(Event.ADDED_TO_STAGE, added);

	}

	function added(e:Event):Void
	{
		removeEventListener(Event.ADDED_TO_STAGE, added);
		addEventListener(MouseEvent.MOUSE_WHEEL, onWheel);
	}

	function onWheel(e:MouseEvent):Void
	{
		try {
			buttons[selectedIdx].deselect();
			selectedIdx -= e.delta;
			if (selectedIdx < 0)
			{
				selectedIdx = 0;
			}
			else if (selectedIdx > buttons.length -1)
			{
				selectedIdx = buttons.length - 1;
			}
			this.invalid = true;
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function add(button:Button)
	{
		button.addEventListener(MouseEvent.CLICK, selectButton);
		buttons.push(button);
		addChild(button);
		addedTo = true;
		this.invalid = true;
	}

	function selectButton(e:MouseEvent):Void
	{
		try {
			buttons[selectedIdx].deselect();
			selectedIdx = getChildIndex(cast (e.currentTarget, Button));
			this.invalid = true;
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function resize()
	{
		try
		{
			var maxHeight:Int = 0;
			for (sprite in buttons)
			{
				maxHeight = cast Math.max(sprite.height, maxHeight);
			}
			maxHeight = Math.ceil(maxHeight);

			var middleIdx = Math.floor(stage.stageHeight / maxHeight / 2);
			var idx:Int = 0;
			for (sprite in buttons)
			{
				sprite.y = (idx - Math.min(Math.max(0, buttons.length-2*middleIdx), Math.max(0, selectedIdx-middleIdx))) * maxHeight;
				idx++;
			}
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function removeAll()
	{
		for (button in buttons)
		{
			button.removeEventListener(MouseEvent.CLICK, selectButton);
			removeChild(button);
		}

		while (buttons.length > 0)
		{
			buttons.pop();
		}

		selectedIdx = 0;
	}

}