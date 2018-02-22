package cn.ypjalt.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthCodeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedImage image = new BufferedImage(90, 36, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		Random r = new Random();
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		g.fillRect(0, 0, 100, 300);
		g.setFont(new Font(null, Font.BOLD, 24));
		String code = getText(4);
		// 把该随机数绑定到session上
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		g.setColor(new Color(0, 0, 0));
		// 把验证码画到模板上
		g.drawString(code, 15, 25);
		response.setContentType("image/jpeg");
		OutputStream os = response.getOutputStream();
		ImageIO.write(image, "jpeg", os);
	}

	private String getText(int a) {
		String str = "qwertyuioplkjhgfdsazxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
		// 存储生成字符
		String st = "";
		Random r = new Random();
		for (int i = 0; i < a; i++) {
			st += str.charAt(r.nextInt(str.length()));
		}
		return st;
	}

}
