 interface User {
    id: string;
    name: string;
    email: string;
    password: string;
    role: string;
    avatar?: string;
  }

  class UserImpl implements User {
    id: string;
    name: string;
    email: string;
    password: string;
    role: string;
    avatar?: string;
  
    constructor(id: string, name: string, email: string, password: string, role: string, avatar?: string) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.role = role;
      this.avatar = avatar;
    }
  }
  
  // Exportez cette classe
  export { UserImpl,User };