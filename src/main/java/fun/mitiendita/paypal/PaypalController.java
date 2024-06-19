package fun.mitiendita.paypal;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import fun.mitiendita.service.PagadoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

	@Autowired
	private PagadoService pagadoService;

	private final PaypalService paypalService;

	@PostMapping("/payment/create")
	public RedirectView createPayment(
			@RequestParam("amount") String amount
	) {
		try {
			String cancelUrl = "https://app.mitiendita.fun/payment/cancel";
			String successUrl = "https://app.mitiendita.fun/payment/success";

			Payment payment = paypalService.createPayment(
				Double.valueOf(amount),
				"USD",
				"paypal",
				"sale",
				"Payment desccription",
				cancelUrl, 
				successUrl
			);
			
			for (Links links: payment.getLinks()) {
				if (links.getRel().equals("approval_url")) {
					return new RedirectView(links.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			log.error("Error occured: ", e);
		}
		
		return new RedirectView("/payment/error");
	}
	
	@GetMapping("payment/success")
	public String paymentSuccess(
			@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId,
			HttpServletRequest request
	) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				// Llamar al servicio para marcar como pagado
				pagadoService.marcarComoPagado(request);

				return "paymentSuccess";
			}
					
		} catch (PayPalRESTException e) {
			log.error("Error occured: ", e);
		}

		return "paymentSuccess";
	}

	@GetMapping("payment/cancel")
	public String paymentCancel() {
		return "paymentCancel";
	}
	
	@GetMapping("payment/error")
	public String paymentError() {
		return "paymentError";
	}
	
}
