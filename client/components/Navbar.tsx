"use client"

import {
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  NavbarMenuToggle,
  NavbarMenu,
  NavbarMenuItem,
  Link,
  Button,
  Dropdown,
  DropdownTrigger,
  DropdownMenu,
  DropdownItem,
  Avatar,
} from "@nextui-org/react"
import { useState } from "react"
import { useAuth } from "@/contexts/AuthContext"
import { useRouter } from "next/navigation"

export default function NavigationBar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false)
  const { user, logout } = useAuth()
  const router = useRouter()

  const menuItems = ["Find Tasks", "How it Works", "Become an Errand Boy", "About", "Contact"]

  const handleLogout = () => {
    logout()
    router.push("/")
  }

  return (
    <Navbar onMenuOpenChange={setIsMenuOpen} className="bg-white/80 backdrop-blur-md">
      <NavbarContent>
        <NavbarMenuToggle aria-label={isMenuOpen ? "Close menu" : "Open menu"} className="sm:hidden" />
        <NavbarBrand>
          <Link href="/" className="font-bold text-2xl text-primary">
            Errand GO
          </Link>
        </NavbarBrand>
      </NavbarContent>

      <NavbarContent className="hidden sm:flex gap-4" justify="center">
        <NavbarItem>
          <Link color="foreground" href="/tasks">
            Find Tasks
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link color="foreground" href="/how-it-works">
            How it Works
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link color="foreground" href="/become-errand-boy">
            Become an Errand Boy
          </Link>
        </NavbarItem>
      </NavbarContent>

      <NavbarContent justify="end">
        {user ? (
          <Dropdown placement="bottom-end">
            <DropdownTrigger>
              <Avatar
                isBordered
                as="button"
                className="transition-transform"
                color="primary"
                name={user.username}
                size="sm"
                src={`/placeholder.svg?height=32&width=32&text=${user.username[0].toUpperCase()}`}
              />
            </DropdownTrigger>
            <DropdownMenu aria-label="Profile Actions" variant="flat">
              <DropdownItem key="profile" className="h-14 gap-2">
                <p className="font-semibold">Signed in as</p>
                <p className="font-semibold">{user.email}</p>
              </DropdownItem>
              <DropdownItem key="dashboard" onClick={() => router.push("/dashboard")}>
                Dashboard
              </DropdownItem>
              <DropdownItem key="profile_page" onClick={() => router.push("/profile")}>
                My Profile
              </DropdownItem>
              <DropdownItem key="notifications" onClick={() => router.push("/notifications")}>
                Notifications
              </DropdownItem>
              <DropdownItem key="logout" color="danger" onClick={handleLogout}>
                Log Out
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
        ) : (
          <NavbarItem className="flex gap-2">
            <Button as={Link} color="primary" href="/login" variant="flat">
              Login
            </Button>
            <Button as={Link} color="primary" href="/register" variant="solid">
              Sign Up
            </Button>
          </NavbarItem>
        )}
      </NavbarContent>

      <NavbarMenu>
        {menuItems.map((item, index) => (
          <NavbarMenuItem key={`${item}-${index}`}>
            <Link
              color={index === 2 ? "primary" : index === menuItems.length - 1 ? "danger" : "foreground"}
              className="w-full"
              href="#"
              size="lg"
            >
              {item}
            </Link>
          </NavbarMenuItem>
        ))}
      </NavbarMenu>
    </Navbar>
  )
}
