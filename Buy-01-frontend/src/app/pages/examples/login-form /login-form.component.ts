import { Component } from "@angular/core";
import { UserImpl } from "src/app/models/Users"; // Assurez-vous que le chemin est correct
import { UserService } from "src/app/services/user.service";

@Component({
  selector: "app-login-form",
  templateUrl: "./login-form.component.html",
})
export class LoginFormComponent {
  user: { email: string; password: string } = { email: "", password: "" };

  constructor(private userService: UserService) {}

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
      },
      (error) => {
        console.error("Erreur lors de la création de l'utilisateur:", error);
      }
    );
  }
}
  