import { Component } from '@angular/core';

@Component({
  selector: 'app-image-uploader',
  template: `
  <label for="image_input">Avatar </label>
    <div id ="image_input">
      <img class=" img-fluid rounded-circle" 
           [src]="imageUrl" 
           
           alt="User Image" 
           style="width: 70px; height: 70px; object-fit: cover; cursor: pointer;"
           (click)="triggerUpload()">
      <input type="file" 
             accept="image/*" 
             (change)="onFileChange($event)" 
             style="display: none;" 
             id="imageInput"
             
             name = "image">
    </div>
  `,
  styles: [`
    .img-center {
      margin: 0 auto;
    }
  `]
})
export class ImageUploaderComponent {
  imageUrl: string | ArrayBuffer | null = 'assets/upload-icon-20632.png';

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
    const input = document.getElementById('imageInput') as HTMLInputElement;
    input.click();
  }
}