package serilogj.parameters;

import serilogj.events.LogEventProperty;
import serilogj.events.MessageTemplate;

import java.util.ArrayList;

public class MessageTemplateProcessorResult {
	public MessageTemplate template;
	public ArrayList<LogEventProperty> properties;
}
