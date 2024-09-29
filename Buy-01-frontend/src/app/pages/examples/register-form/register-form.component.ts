import { Component } from "@angular/core";
// import { ImageUploaderComponent } from './image-uploader/image-uploader.component';

@Component({
  selector: "app-register-form",
  templateUrl: "./register-form.component.html",
  styleUrls: ["./register-form.component.scss"],
})
export class RegisterFormComponent {
  imageUrl: string | ArrayBuffer | null = "assets/upload-icon-20632.png";

  onSubmit(form: any) {
    const formData = new FormData();
    for (const key in form.value) {
      formData.append(key, form.value[key]);
    }
    if (this.selectedFile) {
      formData.append('image', this.selectedFile, this.selectedFile.name);
    }
    console.log("Form data:", formData.get("image"));
    // Vous pouvez maintenant envoyer formData à votre backend
  }

  onImageChange(imageUrl: string) {
    this.imageUrl = imageUrl;
  }
  selectedFile: File | null = null;

  // ... autres méthodes ...

  onFileChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      this.selectedFile = target.files[0];
      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        if (e.target && e.target.result) {
          this.imageUrl = e.target.result as string | ArrayBuffer;
        }
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }
  triggerUpload(): void {
    const input = document.getElementById("imageInput") as HTMLInputElement;
    input.click();
  }
}
