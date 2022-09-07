allUsers();

async function allUsers() {
    const usersTable = document.querySelector('#tableAllUsers tbody');
    let temp = ""
    fetch("http://localhost:8080/api/users")
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                temp += `
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(e => " " + e.name.substring(5))}</td>
                <td><button type="button" class="btn btn-info" data-toggle="modal"
                                data-action="edit" data-id="${user.id}" data-target="#editModal">Изменить</button></td>
                <td><button type="button" class="btn btn-danger" data-toggle="modal"
                                data-action="delete" data-id="${user.id}" data-target="#deleteModal">Удалить</button></td>
            </tr>    
            `;
            })
            usersTable.innerHTML = temp;
        })
}