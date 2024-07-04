package utils.creator;

import utils.prompts.Promptable;
public interface Creatable extends Promptable
{
	String displayState();
}