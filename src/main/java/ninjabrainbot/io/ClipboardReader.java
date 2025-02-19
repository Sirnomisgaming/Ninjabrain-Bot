package ninjabrainbot.io;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.concurrent.atomic.AtomicBoolean;

import ninjabrainbot.event.IObservable;
import ninjabrainbot.event.ObservableField;
import ninjabrainbot.io.preferences.NinjabrainBotPreferences;

public class ClipboardReader implements IClipboardProvider, Runnable {

	private NinjabrainBotPreferences preferences;

	Clipboard clipboard;
	String lastClipboardString;

	private AtomicBoolean forceReadLater;

	ObservableField<String> clipboardString;

	public ClipboardReader(NinjabrainBotPreferences preferences) {
		this.preferences = preferences;
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboardString = new ObservableField<String>(null, true);
		lastClipboardString = "";
		forceReadLater = new AtomicBoolean(false);
	}

	public IObservable<String> clipboardText() {
		return clipboardString;
	}

	public void forceRead() {
		forceReadLater.set(true);
	}

	@Override
	public void run() {
		while (true) {
			boolean read = !preferences.altClipboardReader.get();
			if (preferences.altClipboardReader.get() && forceReadLater.get()) {
				read = true;
				// Sleep 0.1 seconds to let the game update the clipboard
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				if (read && clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
					String clipboardString = null;
					clipboardString = (String) clipboard.getData(DataFlavor.stringFlavor);
					if (clipboardString != null && !lastClipboardString.equals(clipboardString)) {
						onClipboardUpdated(clipboardString);
						lastClipboardString = clipboardString;
					}
				}
			} catch (Exception e) {
			}
			// Sleep 0.1 seconds
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void onClipboardUpdated(String clipboard) {
		clipboardString.set(clipboard);
	}

}
