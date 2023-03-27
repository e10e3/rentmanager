package com.epf.rentmanager.ui.cli;

public final class CliManager {
	private CliManager() {
	}

	/**
	 *  Executes the CLI app.
	 * @param args The command-line arguments
	 */
	public static void main(final String[] args) {
		Menu menuApp = new Menu();
		menuApp.entryPoint();
	}
}
