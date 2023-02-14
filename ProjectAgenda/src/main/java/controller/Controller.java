package controller;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/controller", "/main", "/insert", "/editar", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();

	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		
		if (action.equals("/main")) {
			carregarContatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);

		} else if (action.equals("/editar")) {
			listarContato(request, response);

		} else if (action.equals("/update")) {
			updateContato(request, response);

		} else if (action.equals("/delete")) {
			deletarContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Carregar contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void carregarContatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// carrega os contatos salvos no banco
		List<JavaBeans> listaContatos = dao.listarContatos();

		// primeira coisa setar o atributo contato com a lista
		request.setAttribute("contatos", listaContatos);
		// fazendo o dispatcher para a pagina agenda
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

	}

	/**
	 * Novo contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setNome(request.getParameter("nome"));
		contato.setTelefone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		dao.inserirContato(contato);
		response.sendRedirect("main");

	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idContato = request.getParameter("idcon");

		contato = dao.getContatoById(idContato);

		// mandando as informações do contato para a classe editar.jsp
		request.setAttribute("contatoSelecionado", contato);
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

	}

	/**
	 * Update contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void updateContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setId(Integer.parseInt(request.getParameter("idcon")));
		contato.setNome(request.getParameter("nome"));
		contato.setTelefone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		dao.atualizarContato(contato);

		response.sendRedirect("main");

	}

	/**
	 * Deletar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int respostaId = Integer.parseInt(request.getParameter("idCont"));

		dao.deleteContatoById(respostaId);

		response.sendRedirect("main");

	}

	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document();

		try {
			// setando o nome do documento na resposta
			response.setContentType("apllication/pdf");
			// nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");

			// Criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());

			// abrir o documento
			documento.open();

			documento.add(new Paragraph("Lista de contatos"));
			documento.add(new Paragraph(" "));
			// criar uma tabela

			PdfPTable tabela = new PdfPTable(3);

			// criar cabeçalho tabela
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));

			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);

			// popular tabela
			List<JavaBeans> lista = dao.listarContatos();

			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getTelefone());
				tabela.addCell(lista.get(i).getEmail());

			}
			documento.add(tabela);

		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar o relatorio em pdf" + e);
		} finally {
			documento.close();
		}

	}
}

