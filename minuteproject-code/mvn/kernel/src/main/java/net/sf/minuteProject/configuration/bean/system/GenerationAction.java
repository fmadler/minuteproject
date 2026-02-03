package net.sf.minuteProject.configuration.bean.system;

import java.io.File;
import java.io.IOException;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Targets;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class GenerationAction extends AbstractConfiguration {

	private static Logger logger = Logger.getLogger(GenerationAction.class);
	private String command;
	private boolean isAbsolutePath;
	private Targets targets;

	public void run() {
		if (isValid()) {
			String commandLine = getCommandLine();
			try {
				ProcessBuilder pb = new ProcessBuilder(commandLine);
				pb.directory(new File("setdir"));
				Process p = pb.start();
//				Runtime.getRuntime().exec("cmd /c start "+commandLine);
				logger.error(">>> start command " + commandLine);
			} catch (IOException e) {
				logger.error(">>> cannot start command " + commandLine
						+ " - error :" + e.getMessage());
			}
		} else
			logger.error(">>> cannot start command "+ this.toString());
	}

	private boolean isValid() {
		return !StringUtils.isEmpty(command);
	}

	private String getCommandLine() {
		if (isAbsolutePath) {
			return command;
		} else {
			return targets.getOutputdirRoot() + "/" + command;
		}
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public boolean isAbsolutePath() {
		return isAbsolutePath;
	}

	public void setAbsolutePath(boolean isAbsolutePath) {
		this.isAbsolutePath = isAbsolutePath;
	}

	public Targets getTargets() {
		return targets;
	}

	public void setTargets(Targets targets) {
		this.targets = targets;
	}

}
