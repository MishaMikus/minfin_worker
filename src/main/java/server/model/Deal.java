package server.model;

public class Deal {
    private String url;//buy or sell depends on URL
    private Integer id;
    private String minfin_id;//<div class="au-deal js-au-deal dfMfs0" data-bid="102980775" tabindex="0">
    private String time;//<span class="au-deal-time">11:10</span>
    private String date;//current page parsing date
    private String currencyRate; //<span class="au-deal-currency">26,80</span>
    private String sum;//<span class="au-deal-sum">10 000<label title="доллары США">$</label></span>
    private String currency;//<label title="доллары США">$</label>
    private String phone;//<div class="au-dealer-phone"><span class="c-desc">098</span> <a class="js-showPhone au-dealer-phone-xxx" data-mf="3" data-status="3"    data-timer="1559635812" data-bid-id="102980775"><span class="c-desc">xxx-x</span></a><span class="c-desc">4-15</span></div>
    private String msg;//<div class="au-msg-wrapper js-au-msg-wrapper">Сихів_Довженка 2 ,целиком, Можна частинами, Можна більше</div>
}