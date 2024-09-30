import { Component } from "@angular/core";
import { UserImpl } from "src/app/models/Users"; // Assurez-vous que le chemin est correct
import { UserService } from "src/app/services/user.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-login-form",
  templateUrl: "./login-form.component.html",
})
export class LoginFormComponent {
  user: { email: string; password: string } = { email: "", password: "" };
  private router: Router;
  constructor(private userService: UserService, router: Router) {}

  onSubmit(form: any) {
    console.log(this.user); // Vous pouvez accéder directement aux valeurs via `this.user`
    const newUser = new UserImpl(
      "",
      "",
      this.user.email,
      this.user.password,
      ""
    );

    this.userService.loginUser(newUser).subscribe(
      (response) => {
        console.log("Utilisateur créé avec succès:", response);
        localStorage.setItem("token", response.token);

        this.saveUserData(response.token); // Décoder et stocker les données de l'utilisateur
      },
      (error) => {
        console.error("Erreur lors de la création de l'utilisateur:", error);
      }
    );
  }
  // Fonction pour décoder le token et stocker les données de l'utilisateur
  saveUserData(token: string) {
    console.log("ds;dddddddddddddddddddddddddddddddddddddddddd");

    const tokenPayload = JSON.parse(atob(token.split(".")[1])); // Décodage du token JWT
    console.log("Données décodées du token:", tokenPayload);

    // Stocker les données de l'utilisateur dans un état ou dans le localStorage
    localStorage.setItem("userData", JSON.stringify(tokenPayload));

    // Optionnel: créer un state local (si tu utilises un state management comme NgRx)
    // this.store.dispatch(loginSuccess({ user: tokenPayload }));
  }
}
