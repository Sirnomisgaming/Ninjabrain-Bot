package ninjabrainbot.gui.themeeditor.panels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import ninjabrainbot.event.IDisposable;
import ninjabrainbot.event.ISubscribable;
import ninjabrainbot.event.SubscriptionHandler;
import ninjabrainbot.gui.components.inputfields.HexThemedTextField;
import ninjabrainbot.gui.components.layout.LabeledField;
import ninjabrainbot.gui.components.panels.ThemedPanel;
import ninjabrainbot.gui.style.StyleManager;
import ninjabrainbot.util.I18n;

public class ColorPickerPanel extends ThemedPanel implements IDisposable {

	private static final long serialVersionUID = -1239458552267457429L;

	private static final int GAP = 10;

	ColorChooserPanel colorChooserPanel;

	SubscriptionHandler sh = new SubscriptionHandler();

	public ColorPickerPanel(StyleManager styleManager) {
		super(styleManager);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

		colorChooserPanel = new ColorChooserPanel();
		HexThemedTextField hexTextField = new HexThemedTextField(styleManager);
		hexTextField.setColor(colorChooserPanel.getColor());

		add(new LabeledField(styleManager, I18n.get("settings.themeeditor.hex_colon"), hexTextField, false));
		add(colorChooserPanel);

		sh.add(colorChooserPanel.whenColorChanged().subscribe(color -> hexTextField.setColor(color)));
		sh.add(hexTextField.whenTextChanged().subscribe(text -> colorChooserPanel.setColor(Color.decode(text))));
	}

	public void setColor(Color color) {
		colorChooserPanel.setColor(color);
	}

	public ISubscribable<Color> whenColorChanged() {
		return colorChooserPanel.whenColorChanged();
	}

	@Override
	public void dispose() {
		sh.dispose();
	}

}
