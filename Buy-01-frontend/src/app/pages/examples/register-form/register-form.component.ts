import { Component } from "@angular/core";
import { User, UserImpl } from "src/app/models/Users"; // Assurez-vous que le chemin est correct
import { UserService } from "src/app/services/user.service";

@Component({
  selector: "app-register-form",
  templateUrl: "./register-form.component.html",
  styleUrls: ["./register-form.component.scss"],
})
export class RegisterFormComponent {
  imageUrl: string | ArrayBuffer | null = "./assets/upload-icon-20632.png";
  avatarForm: FormData | null = null;
  user: User | null = null;
  email: string = "";
  
  constructor(private userService: UserService) {}

  onEmailChange(event: Event) {
    const target = event.target as HTMLInputElement;
    console.log("Email changé :", target.value);
    this.email = `"${target.value}"`;

    // Récupérer le file qui est dans avatarForm et changer le nom
    if (this.avatarForm) {
      const file = this.avatarForm.get("file") as File;
      if (file) {
        const newFileName = `${this.email}.${file.type.split("/")[1]}`;

        // Créer un nouveau FormData avec le fichier renommé
        const newFormData = new FormData();
        newFormData.append("file", file, newFileName);

        // Remplacer l'ancien avatarForm par le nouveau
        this.avatarForm = newFormData;
      }
    }
  }

  onSubmit(form: any) {
    const newUser = new UserImpl(
      form.value.id,
      form.value.name,
      form.value.email,
      form.value.password,
      form.value.role
    );
    this.user = newUser;

    // Send avatar if available
    console.log(this.avatarForm);
    if (this.avatarForm) {
      this.userService.sendAvatar(this.avatarForm).subscribe(
        (avatarResponse) => {
          console.log("Avatar uploaded successfully:", avatarResponse);
          // You might want to update the user with the avatar URL here
          this.createUser(newUser);
        },
        (error) => {
          console.error("Error uploading avatar:", error);
          // You might still want to create the user even if avatar upload fails
          this.createUser(newUser);
        }
      );
    } else {
      this.createUser(newUser);
    }
  }

  private createUser(newUser: UserImpl) {
    this.userService.createUser(newUser).subscribe(
      (response) => {
        console.log("Utilisateur créé avec succès:", response);
        // Ajoutez ici votre logique pour gérer la réponse positive
      },
      (error) => {
        console.error("Erreur lors de la création de l'utilisateur:", error);
        // Ajoutez ici votre logique pour gérer les erreurs
      }
    );
  }

  onImageChange(imageUrl: string) {
    this.imageUrl = imageUrl;
  }

  onFileChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      const file = target.files[0];
      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        if (e.target && e.target.result) {
          this.imageUrl = e.target.result as string | ArrayBuffer;
        }
      };
      reader.readAsDataURL(file);

      // Update avatarForm when a file is uploaded
      this.avatarForm = new FormData();
      this.avatarForm.append(
        "file",
        file,
        this.email + "." + file.type.split("/")[1]
      );
    }
  }

  triggerUpload(): void {
    const input = document.getElementById("imageInput") as HTMLInputElement;
    input.click();
  }
}
