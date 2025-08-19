/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author ADMIN
 */
import java.io.*;
import java.io.UTFDataFormatException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

class MyFileChoose extends javax.swing.JFileChooser{    
//    javax.swing.JComboBox encodeC;
    javax.swing.JPanel panel;
    @Override
    protected javax.swing.JDialog createDialog(java.awt.Component parent)throws java.awt.HeadlessException {
        javax.swing.JDialog dialog = super.createDialog(parent);
        dialog.setSize(500, 400);
        dialog.setResizable(false);
        panel = new javax.swing.JPanel(new java.awt.FlowLayout());
        dialog.add(panel, java.awt.BorderLayout.SOUTH);

        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Java(*.java)", "java"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Web(*.html, *.xml, *.php, *.js ...)", "html", "htm", "xhtml", "js", "jsp", "php", "php3", "phtml", "xml", "css", "shtml", "shtm", "asp"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Pascal, ASM, D(*.pas, *.asm, *.d ...)", "pas", "inc", "asm", "d"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("C Source(*.c, *.cpp, *.h,...)", "cpp", "c", "cs", "h", "hpp", "hxx", "cxx", "cc"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("System(*.reg, *.ini, *.inf ...)", "url", "reg", "ini", "inf", "bat", "cmd", "nt"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Visual Basic(*.vb, *.vbs)", "vb", "vbs"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Flash AS(*.as, *.mx)", "as", "mx"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("AutoIt(*.au3)", "au3"));
        this.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Unix Shell(*.sh, *.bsh)", "sh", "bsh"));
        this.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files(*.txt)", "txt"));
        return dialog;
    }
}

class fileChoose extends javax.swing.JFrame{
    String path;
    
    public String getFilePath() {
    MyFileChoose fileBrowse = new MyFileChoose();
    fileBrowse.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    int result = fileBrowse.showOpenDialog(this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
        this.path = fileBrowse.getSelectedFile().getAbsolutePath();
    } else {
        // Người dùng đã hủy chọn tệp, có thể xử lý hoặc trả về giá trị mặc định
        return ""; // hoặc null, hoặc thông báo khác tùy ý
    }

    return this.path;
}
    
    public static void main(String[] args){
    }
}
