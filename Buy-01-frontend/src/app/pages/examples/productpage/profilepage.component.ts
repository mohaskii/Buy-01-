import { Component, OnInit, OnDestroy } from "@angular/core";

@Component({
  selector: "app-productpage",
  templateUrl: "product.component.html"
})
export class ProductComponent implements OnInit, OnDestroy {
  isCollapsed = true;
  constructor() {}

  ngOnInit() {
    var body = document.getElementsByTagName("body")[0];
    body.classList.add("product-page");
  }
  ngOnDestroy() {
    var body = document.getElementsByTagName("body")[0];
    body.classList.remove("product-page");
  }
}
