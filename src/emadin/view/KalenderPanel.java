/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emadin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class KalenderPanel extends javax.swing.JPanel {
    // Variabel kalender

    private YearMonth bulan;
    private LocalDate tanggalDipilih;

// Komponen kalender
    private JLabel lblBulan;
    private JPanel pTanggal;

// Menyimpan tanggal yang memiliki kegiatan
    private Set<LocalDate> tanggalKegiatan = new HashSet<>();

// Warna kalender
    private final Color HIJAU = Color.decode("#00613E");
    private final Color PUTIH = Color.WHITE;
    private final Color TEKS = Color.decode("#263238");
    private final Color ABU = Color.decode("#667085");
    private final Color HIJAU_MUDA = Color.decode("#E6F2E9");

    public KalenderPanel() {

        // Menentukan bulan awal
        bulan = YearMonth.of(2026, 10);

        // Menentukan tanggal terpilih
        tanggalDipilih = LocalDate.of(2026, 10, 21);

        // Contoh tanggal yang memiliki kegiatan
        tanggalKegiatan.add(LocalDate.of(2026, 10, 2));
        tanggalKegiatan.add(LocalDate.of(2026, 10, 9));
        tanggalKegiatan.add(LocalDate.of(2026, 10, 16));
        tanggalKegiatan.add(LocalDate.of(2026, 10, 23));
        tanggalKegiatan.add(LocalDate.of(2026, 10, 29));
        tanggalKegiatan.add(LocalDate.of(2026, 10, 30));

        // Mengatur layout utama
        setLayout(new BorderLayout(10, 15));

        // Warna background
        setBackground(PUTIH);

        // Ukuran kalender
        setPreferredSize(new Dimension(400, 463));

        // Mengatur jarak tepi
        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // Membuat header kalender
        buatHeader();

        // Membuat panel tanggal
        pTanggal = new JPanel(
                new GridLayout(7, 7, 5, 5)
        );

        pTanggal.setBackground(PUTIH);

        // Memasukkan panel tanggal
        add(pTanggal, BorderLayout.CENTER);

        tampilkanKalender();
    }

    private void buatHeader() {

        // Panel header
        JPanel pHeader = new JPanel(
                new BorderLayout()
        );

        pHeader.setBackground(PUTIH);

        // Label nama bulan
        lblBulan = new JLabel();

        lblBulan.setFont(
                new Font("SansSerif", Font.BOLD, 17)
        );

        lblBulan.setForeground(TEKS);

        // Tombol bulan sebelumnya
        JButton btnSebelumnya = new JButton("‹");

        // Tombol bulan berikutnya
        JButton btnBerikutnya = new JButton("›");

        // Mengatur tampilan tombol
        aturTombolNavigasi(btnSebelumnya);
        aturTombolNavigasi(btnBerikutnya);

        // Event bulan sebelumnya
        btnSebelumnya.addActionListener(e -> {

            bulan = bulan.minusMonths(1);

            tampilkanKalender();

        });

        // Event bulan berikutnya
        btnBerikutnya.addActionListener(e -> {

            bulan = bulan.plusMonths(1);

            tampilkanKalender();

        });

        // Panel tombol navigasi
        JPanel pNavigasi = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 5, 0)
        );

        pNavigasi.setBackground(PUTIH);

        pNavigasi.add(btnSebelumnya);
        pNavigasi.add(btnBerikutnya);

        // Memasukkan komponen
        pHeader.add(lblBulan, BorderLayout.WEST);

        pHeader.add(pNavigasi, BorderLayout.EAST);

        add(pHeader, BorderLayout.NORTH);

    }

    private void aturTombolNavigasi(JButton tombol) {

        tombol.setFont(
                new Font("SansSerif", Font.BOLD, 22)
        );

        tombol.setForeground(ABU);

        tombol.setBackground(PUTIH);

        tombol.setFocusPainted(false);

        tombol.setBorderPainted(false);

        tombol.setContentAreaFilled(false);

        tombol.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

    }

    private void tampilkanKalender() {

        // Membersihkan tanggal sebelumnya
        pTanggal.removeAll();

        // Menampilkan nama bulan dan tahun
        lblBulan.setText(
                bulan.format(
                        DateTimeFormatter.ofPattern(
                                "MMMM yyyy",
                                new Locale("id", "ID")
                        )
                )
        );

        // Nama hari
        String[] hari = {
            "Sen", "Sel", "Rab",
            "Kam", "Jum", "Sab", "Min"
        };

        // Membuat header nama hari
        for (String namaHari : hari) {

            JLabel label = new JLabel(
                    namaHari,
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font("SansSerif", Font.BOLD, 11)
            );

            label.setForeground(ABU);

            pTanggal.add(label);
        }

        // Menghitung tanggal awal kalender
        LocalDate tanggalAwal = bulan.atDay(1);

        int offset
                = tanggalAwal.getDayOfWeek().getValue() - 1;

        LocalDate tanggalMulai
                = tanggalAwal.minusDays(offset);

        // Membuat 42 kotak tanggal
        for (int i = 0; i < 42; i++) {

            LocalDate tanggalIni
                    = tanggalMulai.plusDays(i);

            JButton tombol = new JButton(
                    String.valueOf(tanggalIni.getDayOfMonth())
            );
            // Memeriksa apakah tanggal memiliki kegiatan
            boolean adaKegiatan
                    = tanggalKegiatan.contains(tanggalIni);

// Jika ada kegiatan, tampilkan titik hijau
            if (adaKegiatan) {

                tombol.setText(
                        "<html><center>"
                        + tanggalIni.getDayOfMonth()
                        + "<br><font color='#00613E'>●</font>"
                        + "</center></html>"
                );

            }
            tombol.setFont(
                    new Font("SansSerif", Font.PLAIN, 12)
            );

            tombol.setFocusPainted(false);
            tombol.setBorderPainted(false);
            tombol.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            // Warna dasar
            tombol.setBackground(PUTIH);

            // Tanggal bulan aktif
            if (YearMonth.from(tanggalIni).equals(bulan)) {
                tombol.setForeground(TEKS);
            } else {
                tombol.setForeground(
                        Color.decode("#A0B5A5")
                );
            }

            // Tanggal yang dipilih
            if (tanggalIni.equals(tanggalDipilih)) {
                tombol.setBackground(HIJAU);
                tombol.setForeground(PUTIH);
            }

            // Event klik tanggal
            tombol.addActionListener(e -> {

                tanggalDipilih = tanggalIni;

                bulan = YearMonth.from(tanggalIni);
                
                firePropertyChange("tanggalDipilih", null, tanggalDipilih);
                
                tampilkanKalender();

                System.out.println(
                        "Tanggal dipilih: " + tanggalDipilih
                );
            });

            pTanggal.add(tombol);
        }

        pTanggal.revalidate();
        pTanggal.repaint();
    }
}
