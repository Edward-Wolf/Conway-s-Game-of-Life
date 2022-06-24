import java.awt.*;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class Grid extends JPanel
{

    private int _rows = 10;
    private int _cols = 15;
    private int _gap = 5;

    public JToggleButton[][] grid = new JToggleButton[_rows][_cols];
    private int[][] _grid = new int[_rows][_cols];

    public void initialize(){
        for(int i = 0; i < _rows; i++){
            for(int j = 0; j < _cols; j++){
                _grid[i][j] = 0;
            }
        }
    }

    public void rules(){
        int[][] temp = new int[_rows][_cols];
        for(int i = 0; i < _rows; i++){
            for(int j = 0; j < _cols; j++){
                int na = neighbors(i, j);
                if (_grid[i][j] == 1){
                    if(na == 2 || na == 3){
                        temp[i][j] = 1;
                    } else {
                        temp[i][j] = 0;
                    }
                }
                if (_grid[i][j] == 0){
                    if(na == 3){
                        temp[i][j] = 1;
                    } else {
                        temp[i][j] = 0;
                    }
                }
            }
        }

        _grid = temp;

        for(int i = 0; i < _rows; i++){
            for(int j = 0; j < _cols; j++){
                if (_grid[i][j] == 0){
                    grid[i][j].setSelected(false);
                } else {
                    grid[i][j].setSelected(true);
                }
            }
        }
    }

    private int neighbors(int x, int y) {
        int l;
        int r;
        int t;
        int b;

        if(x==0){
            l = _rows-1;
            r = 1;
        }
        else if(x==(_rows-1)){
            r = 0;
            l = _rows-2;
        }
        else {
            l = x-1;
            r = x+1;
        }

        if(y==0){
            t = _cols-1;
            b = 1;
        }
        else if(y==(_cols - 1)){
            b = 0;
            t = _cols-2;
        }
        else {
            b = y + 1;
            t = y - 1;
        }

        int total = _grid[l][t] + _grid[x][t] + _grid[r][t] + _grid[l][y] + _grid[r][y] + _grid[l][b] + _grid[x][b] + _grid[r][b];
        return total;
    }

    public Grid() {
        JPanel universPanel = new JPanel();
        universPanel.setLayout(new GridLayout(_rows, _cols));

        ActionListener buttonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                JToggleButton selectedBtn = (JToggleButton) evt.getSource();
                for (int row = 0; row < grid.length; row++) {
                    for (int col = 0; col < grid[row].length; col++) {
                        if (grid[row][col] == selectedBtn) {
                            if(_grid[row][col] == 0){
                                _grid[row][col] = 1;
                            } else {
                                _grid[row][col] = 0;
                            }
                        }
                    }
                }
            }
        };
        for (int row = 0; row < _rows; row++) {
            for (int col = 0; col < _cols; col++) {
                String text = String.format("Button [%d, %d]", row, col);
                grid[row][col] = new JToggleButton();
                grid[row][col].addActionListener(buttonListener);
                universPanel.add(grid[row][col]);
                Color color = Color.decode("#D92CF9");
                UIManager.put("ToggleButton.select", color);
                UIManager.put("ToggleButton.background", Color.BLACK);
                SwingUtilities.updateComponentTreeUI(grid[row][col]);
            }
        }

        Timer timer = new Timer(1000, e -> rules());

        //Buttons
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(0,3));
        JButton starter = new JButton("Start");
        panelButton.add(starter);
        JButton stop = new JButton("Stop");
        panelButton.add(stop);
        JButton clear = new JButton("Clear");
        panelButton.add(clear);

        //Starts the timer
        starter.addActionListener(e ->  timer.start());

        //Stops the timer
        stop.addActionListener(e -> timer.stop());

        //Resets all cells
        clear.addActionListener(e -> {
            for(int i = 0; i < _rows; i++){
                for(int j = 0; j < _cols; j++){
                    grid[i][j].setSelected(false);
                    _grid[i][j] = 0;
                }
            }
        });

        JFrame frame = new JFrame("Grid");
        frame.add(universPanel, BorderLayout.CENTER);
        frame.add(panelButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); //pack after you added all components
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Grid();
            }
        });
    }
}