import { Component } from '@angular/core';
import { UserImpl } from 'src/app/models/Users'; // Assurez-vous que le chemin est correct
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent {
  imageUrl: string | ArrayBuffer | null = "./assets/upload-icon-20632.png";

  constructor(private userService: UserService) {}

  onSubmit(form: any) {
    const newUser = new UserImpl(
      form.value.id,
      form.value.name,
      form.value.email,
      form.value.password,
      form.value.role
    );

    if (this.imageUrl) {
      newUser.avatar = this.imageUrl.toString(); // Convertissez l'image en chaîne si nécessaire
    }

    this.userService.createUser(newUser).subscribe(
      (response) => {
        console.log('Utilisateur créé avec succès:', response);
        // Ajoutez ici votre logique pour gérer la réponse positive
      },
      (error) => {
        console.error('Erreur lors de la création de l\'utilisateur:', error);
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
      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        if (e.target && e.target.result) {
          this.imageUrl = e.target.result as string | ArrayBuffer;
        }
      };
      reader.readAsDataURL(target.files[0]);
    }
  }

  triggerUpload(): void {
    const input = document.getElementById("imageInput") as HTMLInputElement;
    input.click();
  }
} 