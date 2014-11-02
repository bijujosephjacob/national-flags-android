package jabs.nationalflags;

import java.util.HashMap;
import java.util.Iterator;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final int lightGreyColor = Utils.blendColor(Color.BLACK,Color.WHITE, 0.2);
		final int greyColor = Utils.blendColor(Color.BLACK,Color.WHITE, 0.4);
		final int orangeColor = Color.parseColor("#FF8C00");
		final int lightBlueColor = Color.parseColor("#87CEFA");
		
		final Country[] countryList = new Country[]
				{
					new Country("India", orangeColor, Color.WHITE, Color.GREEN),
					new Country("Netherlands", Color.RED, Color.WHITE, Color.BLUE),
					new Country("Iran", Color.GREEN, Color.WHITE, Color.RED),
					new Country("Argentina", lightBlueColor, Color.WHITE, lightBlueColor),
					new Country("Egypt", Color.RED, Color.WHITE, Color.BLACK),
				};
		
		final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		final TableRow tableRow111 = (TableRow) findViewById(R.id.tableRow1_1_1);
		final TableRow tableRow112 = (TableRow) findViewById(R.id.tableRow1_1_2);
		final TableRow tableRow121 = (TableRow) findViewById(R.id.tableRow1_2_1);
		final TableRow tableRow122 = (TableRow) findViewById(R.id.tableRow1_2_2);
		final TableRow tableRow123 = (TableRow) findViewById(R.id.tableRow1_2_3);
		
		final int maxSeekBarValue = seekBar.getMax();
		final double countryScaleLength = (double) (maxSeekBarValue / (countryList.length-1));
		
		tableRow111.setBackgroundColor(greyColor);
		tableRow112.setBackgroundColor(lightGreyColor);
		
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(getApplicationContext(), String.valueOf(seekBar.getProgress()), Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				HashMap<Country, Double> countries = getCountriesBasedOnValue(progress);
				Iterator<Country> countriesIterator = countries.keySet().iterator();
				Country country1 = countriesIterator.next();
				Country country2 = countriesIterator.next();
				double ratio = countries.get(country1);
				int[] country1FlagColors = country1.flagColors;
				int[] country2FlagColors = country2.flagColors;

				tableRow121.setBackgroundColor(Utils.blendColor(
						country1FlagColors[0], country2FlagColors[0], ratio));
				tableRow122.setBackgroundColor(Utils.blendColor(
						country1FlagColors[1], country2FlagColors[1], ratio));
				tableRow123.setBackgroundColor(Utils.blendColor(
						country1FlagColors[2], country2FlagColors[2], ratio));
				
				long country1Percentage = Utils.roundToNearestMultiple(ratio * 100, 10); 
				
				String countryRatioFormat = "%s (%d%%) ";
				String country1String = String.format(countryRatioFormat,
						country1.getName(), country1Percentage);
				String country2String = String.format(countryRatioFormat,
						country2.getName(), 100 - country1Percentage);

				setTitle((country1Percentage > 0 ? country1String : "")
						+ (country1Percentage < 100 ? country2String : ""));
			}
			
			private HashMap<Country, Double> getCountriesBasedOnValue(int value) {
				if(value == maxSeekBarValue)
					value = value - 1;
				double valueOnCountryScale = value / countryScaleLength;
				double country1Ratio = 1 - (valueOnCountryScale - (int) valueOnCountryScale);
				
				int country1Index = (int)valueOnCountryScale;
				int country2Index = country1Index + 1;
				
				HashMap<Country, Double> result = new HashMap<Country, Double>();
				result.put(countryList[country1Index], country1Ratio);
				result.put(countryList[country2Index], 1 - country1Ratio);
				return result;
			}
		});
		seekBar.setProgress(1);
		seekBar.setProgress(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.moreInformation) {
			AlertDialog.Builder moreInformationDialogBuilder = new AlertDialog.Builder(
					MainActivity.this);
			// set title
			moreInformationDialogBuilder
					.setTitle("Museum of Modern Art (New York)");

			// set dialog message
			moreInformationDialogBuilder
					.setMessage(
							"This application has been developed by Biju Joseph Jacob" +
							"(bijujosephjacob@gmail.com). The app displays a few of the many" +
							"horizontal tri-colour national flags from around the world.\n" +
							"Inspired by the works of Piet Mondrian and Ben Nicholson.\n\n" +
							"Click below to learn more!")
					.setCancelable(false)
					.setPositiveButton("Visit MOMA",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									// if this button is clicked, close current activity
									Intent visitMoma = new Intent(Intent.ACTION_VIEW)
										.setData(Uri.parse("http://www.moma.org/"));
									startActivity(visitMoma);
								}
							})
					.setNegativeButton("Not Now",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = moreInformationDialogBuilder.create();

			// show it
			alertDialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
