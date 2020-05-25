
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JProgressBar;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class part1 {

	private JFrame frame;
	private JTextField textField;
	private JTextArea txtArea;
	private JLabel lblRunningTimeCounter;
	static List<String> nearestFiveWords = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					part1 window = new part1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public part1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 416, 514);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblPleaseEnterA = new JLabel("Please Enter a Word : ");
		lblPleaseEnterA.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblPleaseEnterA.setBounds(21, 24, 162, 45);
		frame.getContentPane().add(lblPleaseEnterA);

		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		textField.setBounds(181, 31, 184, 34);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

	

		JButton btnProcess = new JButton("SEARCH");
		btnProcess.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nearestFiveWords = new ArrayList<>();
				txtArea.setText("");
				String nearestFiveWordsText = "";
				long start = System.currentTimeMillis();
				try {
					File file = new File("sozluk.txt");
					BufferedReader b = new BufferedReader(new FileReader(file));
					String readLine = "";
					int minDist = 90000;
					int maxDist = 90000;
					while ((readLine = b.readLine()) != null) {
						
						int med = med(textField.getText(), readLine, textField.getText().length(), readLine.length());
						
						boolean isFirstFiveWordFill = false;
						while(!isFirstFiveWordFill){
							if(nearestFiveWords.size() != 5){
								nearestFiveWords.add(med+ " " + readLine);
								break;
							}else{
								isFirstFiveWordFill = true;
								Collections.sort(nearestFiveWords);
								if (nearestFiveWords.get(nearestFiveWords.size() - 1).substring(0,1) != null) {
									maxDist = Integer.parseInt(nearestFiveWords.get(nearestFiveWords.size() - 1).substring(0,1));
								}
								
							}
						}
						if (med < maxDist && isFirstFiveWordFill) {
							Collections.sort(nearestFiveWords);
							nearestFiveWords.remove(nearestFiveWords.get(nearestFiveWords.size() - 1));
							nearestFiveWords.add(med+ " " + readLine );
							Collections.sort(nearestFiveWords);
							String x = nearestFiveWords.get(nearestFiveWords.size() - 1).substring(0,1);
							if (nearestFiveWords.get(nearestFiveWords.size() - 1).substring(0,1) != null) {
								maxDist = Integer.parseInt(nearestFiveWords.get(nearestFiveWords.size() - 1).substring(0,1));
							}
						}
					}
					

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < nearestFiveWords.size(); i++) {
					 nearestFiveWordsText += nearestFiveWords.get(i) + "\n";
				}
				txtArea.setText(nearestFiveWordsText);
				long finish = System.currentTimeMillis();
				long runningTime = finish - start;
				lblRunningTimeCounter.setText(String.valueOf(runningTime) + " ms");
			}
		});
		btnProcess.setBounds(21, 91, 344, 45);
		frame.getContentPane().add(btnProcess);

		JLabel lblNewLabel = new JLabel("Nearest 5 words : ");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblNewLabel.setBounds(21, 154, 147, 34);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblAyseCam = new JLabel("2014510083_Ay\u015Fe_\u00C7AM");
		lblAyseCam.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		lblAyseCam.setHorizontalAlignment(SwingConstants.CENTER);
		lblAyseCam.setBounds(181, 427, 172, 27);
		frame.getContentPane().add(lblAyseCam);
		
		txtArea = new JTextArea();
		txtArea.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtArea.setBounds(21, 199, 344, 147);
		txtArea.setEnabled(false);
		frame.getContentPane().add(txtArea);
		
		JLabel lblRunningTime = new JLabel("Running Time : ");
		lblRunningTime.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblRunningTime.setBounds(21, 371, 114, 34);
		frame.getContentPane().add(lblRunningTime);
		
		lblRunningTimeCounter = new JLabel("");
		lblRunningTimeCounter.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRunningTimeCounter.setBounds(129, 371, 114, 34);
		frame.getContentPane().add(lblRunningTimeCounter);
		
		
	}

	private int med(String str1, String str2, int m, int n) {
		// If first string is empty, the only option is to
		// insert all characters of second string into first
		if (m == 0)
			return n;

		// If second string is empty, the only option is to
		// remove all characters of first string
		if (n == 0)
			return m;

		// If last characters of two strings are same, nothing
		// much to do. Ignore last characters and get count for
		// remaining strings.
		if (str1.charAt(m - 1) == str2.charAt(n - 1))
			return med(str1, str2, m - 1, n - 1);

		// If last characters are not same, consider all three
		// operations on last character of first string, recursively
		// compute minimum cost for all three operations and take
		// minimum of three values.
		return 1 + min(med(str1, str2, m, n - 1), // Insert
				med(str1, str2, m - 1, n), // Remove
				med(str1, str2, m - 1, n - 1) // Replace
		);
	}

	private int min(int x, int y, int z) {
		if (x <= y && x <= z)
			return x;
		if (y <= x && y <= z)
			return y;
		else
			return z;
	}
}
